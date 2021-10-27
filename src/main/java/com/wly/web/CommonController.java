package com.wly.web;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.StrUtil;
import com.wly.entity.Area;
import com.wly.service.AreaService;
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
        // 将文件转换成base64
        return ImgUtil.toBase64DataUri(
                // 读取图片
                ImgUtil.read(file.getInputStream()),
                // 取图片后缀作为图片的类型
                CollectionUtil.getLast(StrUtil.split(file.getOriginalFilename(), "."))
        );
    }

}
