package com.anjsh.web;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.StrUtil;
import com.anjsh.entity.Area;
import com.anjsh.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class CommonController {

    @Autowired
    private AreaService areaAervice;

    /**
     * 查询2级城市
     *
     * @return
     */
    @GetMapping(value = "/findAreaByLevel2")
    public List<Area> findAreaByLevel2(String areaName) {
        return areaAervice.findAreaByLevel(areaName);
    }

    /**
     * 查询3级城市
     *
     * @return
     */
    @GetMapping(value = "/findAreaByLevel3")
    public List<Area> findAreaByLevel3(String areaName, String cityName) {
        return areaAervice.findAreaByLevel(areaName, cityName);
    }

    @PostMapping("upload")
    public String fileUpload(@RequestParam(value = "upload") MultipartFile file) throws IOException {
        return ImgUtil.toBase64DataUri(ImgUtil.read(file.getInputStream()), CollectionUtil.getLast(StrUtil.split(file.getOriginalFilename(), ".")));
    }

}
