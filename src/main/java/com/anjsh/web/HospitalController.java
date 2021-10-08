package com.anjsh.web;

import com.anjsh.dto.OrderHosPageQuery;
import com.anjsh.entity.Hospital;
import com.anjsh.service.HospitalService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author sfturing
 * @date 2017年5月19日
 */
@RestController
@RequestMapping("hos")
public class HospitalController {

    @Resource
    private HospitalService hospitalService;


    @PostMapping("/orderHos")
    public IPage<Hospital> orderHos(@RequestBody OrderHosPageQuery pageQuery) {
        return hospitalService.page(new Page<>(pageQuery.getPageIndex(), pageQuery.getPageSize()), Wrappers.lambdaQuery(Hospital.class)
                .eq(Hospital::getIsOpen, 1)
                .eq(pageQuery.getHospitalNature() != null, Hospital::getHospitalNature, pageQuery.getHospitalNature())
                .eq(pageQuery.getHospitalGrade() != null, Hospital::getHospitalGrade, pageQuery.getHospitalGrade())
                .likeRight(pageQuery.getHospitalName() != null, Hospital::getHospitalName, pageQuery.getHospitalName())
                .likeRight(pageQuery.getProvince() != null, Hospital::getHospitalAddress, pageQuery.getProvince())
                .like(pageQuery.getCity() != null, Hospital::getHospitalAddress, pageQuery.getCity())
                .eq(pageQuery.getDistrict() != null, Hospital::getHospitalArea, pageQuery.getDistrict())
        );
    }

    @PostMapping("add")
    public void add(@RequestBody Hospital hospital) {
        hospitalService.save(hospital);
    }

    @PutMapping("update")
    public void update(@RequestBody Hospital hospital) {
        hospitalService.updateById(hospital);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        hospitalService.removeById(id);
    }

}
