package com.wly;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.wly.utils.LoginHandlerInterceptor;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
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
