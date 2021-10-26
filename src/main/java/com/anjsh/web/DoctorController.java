package com.anjsh.web;

import com.anjsh.dto.OrderDoctorPageQuery;
import com.anjsh.entity.Doctor;
import com.anjsh.entity.Office;
import com.anjsh.exception.Assert;
import com.anjsh.service.DoctorService;
import com.anjsh.service.HospitalService;
import com.anjsh.service.OfficeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Resource
    private HospitalService hospitalService;

    @Resource
    private OfficeService officeService;

    @PostMapping("/allDoctor")
    public IPage<Doctor> orderHos(@RequestBody OrderDoctorPageQuery pageQuery) {
        return doctorService.page(new Page<>(pageQuery.getPageIndex(), pageQuery.getPageSize()), Wrappers.lambdaQuery(Doctor.class)
                .eq(pageQuery.getDoctorTitle() != null, Doctor::getDoctorTitle, pageQuery.getDoctorTitle())
                .eq(pageQuery.getDoctorAdministrative() != null, Doctor::getDoctorAdministrative, pageQuery.getDoctorAdministrative())
                .eq(pageQuery.getDoctorDegree() != null, Doctor::getDoctorDegree, pageQuery.getDoctorDegree())
                .likeRight(pageQuery.getHospitalName() != null, Doctor::getHospitalName, pageQuery.getHospitalName())
                .likeRight(pageQuery.getOfficesName() != null, Doctor::getOfficesName, pageQuery.getOfficesName())
                .likeRight(pageQuery.getDoctorName() != null, Doctor::getDoctorName, pageQuery.getDoctorName())
                .orderByDesc(Doctor::getId)
        );
    }

    @PostMapping("add")
    public void add(@RequestBody Doctor doctor) {
        setDoctor(doctor);
        doctorService.save(doctor);
    }

    private void setDoctor(Doctor doctor) {
        Integer hosId = Assert.notNull(hospitalService.findHosByName(doctor.getHospitalName()), "医院" + doctor.getHospitalName() + "不存在").getId();
        doctor.setHosId(hosId);
        doctor.setOfficeId(Assert.notNull(officeService.getOne(Wrappers.lambdaQuery(Office.class).eq(Office::getHosId, hosId).eq(Office::getOfficesName, doctor.getOfficesName())), "科室" + doctor.getOfficesName() + "不存在").getId());
    }

    @PutMapping("update")
    public void update(@RequestBody Doctor doctor) {
        setDoctor(doctor);
        doctorService.updateById(doctor);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        doctorService.removeById(id);
    }
}
