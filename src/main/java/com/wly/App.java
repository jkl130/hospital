package com.wly;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import com.wly.utils.LoginHandlerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 启动类
 *
 * @author wly
 * @date 2021/10/27
 */
// 标识这是一个springboot项目, 用于包扫描
@SpringBootApplication
// 指定要扫描的mapper类所在的包
@MapperScan("com.wly.mapper")
public class App implements WebMvcConfigurer {

    @Value("${secretId}")
    private String secretId;

    @Value("${secretKey}")
    private String secretKey;


    public static void main(String[] args) {
        // 启动一个springboot项目
        SpringApplication.run(App.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器 除登录接口外全部拦截
        registry.addInterceptor(new LoginHandlerInterceptor())
                // 要拦截的path
                .addPathPatterns("/**")
                // 要排除拦截的path
                .excludePathPatterns("/user/login");
    }

    @Bean(destroyMethod = "shutdown")
    public COSClient cosClient() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        Region region = new Region("ap-guangzhou");
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端
        return new COSClient(cred, clientConfig);
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(List<InnerInterceptor> interceptors) {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 添加插件 添加顺序：动态表名 -> 分页 -> 乐观锁
        mybatisPlusInterceptor.setInterceptors(interceptors);
        return mybatisPlusInterceptor;
    }

    @Bean
    public InnerInterceptor paginationInnerInterceptor() {
        // 分页插件 https://mp.baomidou.com/guide/interceptor-pagination.html#paginationinnerinterceptor
        return new PaginationInnerInterceptor(DbType.MYSQL);
    }

    /**
     * http 消息转换器
     */
    @Bean
    @Order(-1)
    public HttpMessageConverter<Object> httpMessageConverter() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.PrettyFormat,
                // 禁用循环引用
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.IgnoreNonFieldGetter
        );
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFastJsonConfig(fastJsonConfig);
        return converter;
    }
}
