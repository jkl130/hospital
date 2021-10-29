package com.wly.web;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.wly.entity.Area;
import com.wly.service.AreaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 常见的控制器
 *
 * @author wly
 * @date 2021/10/27
 */
@RestController
public class CommonController {

    @Resource
    private AreaService areaService;

    @Resource
    private COSClient cosClient;

    @Value("${bucketName}")
    private String bucketName;

    @GetMapping(value = "/findAreaByLevel2")
    public List<Area> findAreaByLevel2(String areaName) {
        return areaService.findAreaByLevel2(areaName);
    }

    @GetMapping(value = "/findAreaByLevel3")
    public List<Area> findAreaByLevel3(String areaName, String cityName) {
        return areaService.findAreaByLevel3(areaName, cityName);
    }

    /**
     * 图片上传
     *
     * @param file 图片
     * @return {@link String} base64编码的字符串
     */
    @PostMapping("upload")
    public String fileUpload(@RequestParam(value = "upload") MultipartFile file) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        String key = IdUtil.fastSimpleUUID() + "-" + file.getOriginalFilename();
        // 取图片后缀作为图片的类型
        objectMetadata.setContentType("image/" + CollectionUtil.getLast(StrUtil.split(file.getOriginalFilename(), ".")));
        cosClient.putObject(new PutObjectRequest(
                // 指定文件将要存放的存储桶
                bucketName,
                // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
                key,
                file.getInputStream(),
                objectMetadata
        ));

        return "https://" + bucketName + ".cos.ap-guangzhou.myqcloud.com/" + key;
    }

}
