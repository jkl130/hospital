package com.anjsh.web;

import cn.hutool.core.lang.Assert;
import com.anjsh.dto.OrderOfficePageQuery;
import com.anjsh.entity.Office;
import com.anjsh.service.HospitalService;
import com.anjsh.service.OfficeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("office")
public class OfficeController {

    @Resource
    private OfficeService officeService;

    @Resource
    private HospitalService hospitalService;

    @PostMapping("/orderOffice")
    public IPage<Office> orderOffice(@RequestBody OrderOfficePageQuery pageQuery) {
        return officeService.page(new Page<>(pageQuery.getPageIndex(), pageQuery.getPageSize()), Wrappers.lambdaQuery(Office.class)
                .likeRight(pageQuery.getHospitalName() != null, Office::getHospitalName, pageQuery.getHospitalName())
                .likeRight(pageQuery.getOfficesName() != null, Office::getOfficesName, pageQuery.getOfficesName())
                .orderByDesc(Office::getId));
    }

    @PostMapping("add")
    public void add(@RequestBody Office office) {
        office.setHosId(Assert.notNull(hospitalService.findHosByName(office.getHospitalName())).getId());
        officeService.save(office);
    }

    @PutMapping("update")
    public void update(@RequestBody Office office) {
        office.setHosId(Assert.notNull(hospitalService.findHosByName(office.getHospitalName())).getId());
        officeService.updateById(office);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        officeService.removeById(id);
    }
}
