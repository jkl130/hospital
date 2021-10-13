package com.anjsh.web;

import com.anjsh.entity.Area;
import com.anjsh.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AreaController {

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

}
