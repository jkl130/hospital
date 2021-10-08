package com.anjsh.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anjsh.dto.DoctorInfoDTO;
import com.anjsh.dto.OrderDoctorPageQuery;
import com.anjsh.dto.OrderHosPageQuery;
import com.anjsh.entity.CommonCondition;
import com.anjsh.entity.Doctor;
import com.anjsh.entity.Hospital;
import com.anjsh.entity.Office;
import com.anjsh.service.DoctorService;
import com.anjsh.service.HospitalService;
import com.anjsh.utils.PageUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author sfturing
 * @date 2017年5月31日
 */
@RestController
@RequestMapping("doctor")
public class DoctorController {

    @Resource
    private DoctorService doctorService;

    @PostMapping("/allDoctor")
    public IPage<Doctor> orderHos(@RequestBody OrderDoctorPageQuery pageQuery) {
        return doctorService.page(new Page<>(pageQuery.getPageIndex(), pageQuery.getPageSize()), Wrappers.lambdaQuery(Doctor.class)
                .eq(pageQuery.getDoctorTitle() != null, Doctor::getDoctorTitle, pageQuery.getDoctorTitle())
                .eq(pageQuery.getDoctorAdministrative() != null, Doctor::getDoctorAdministrative, pageQuery.getDoctorAdministrative())
                .eq(pageQuery.getDoctorDegree() != null, Doctor::getDoctorDegree, pageQuery.getDoctorDegree())
                .likeRight(pageQuery.getHospitalName() != null, Doctor::getHospitalName, pageQuery.getHospitalName())
                .likeRight(pageQuery.getOfficesName() != null, Doctor::getOfficesName, pageQuery.getOfficesName())
                .likeRight(pageQuery.getDoctorName() != null, Doctor::getDoctorName, pageQuery.getDoctorName())
        );
    }

    @PostMapping("add")
    public void add(@RequestBody Doctor doctor) {
        doctorService.save(doctor);
    }

    @PutMapping("update")
    public void update(@RequestBody Doctor doctor) {
        doctorService.updateById(doctor);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        doctorService.removeById(id);
    }
}
