package com.wly.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wly.dto.DoctorPageQuery;
import com.wly.entity.Doctor;
import com.wly.service.DoctorService;
import com.wly.service.HospitalService;
import com.wly.service.OfficeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 医生控制器
 *
 * @author wly
 * @date 2021/10/27
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

    @GetMapping
    public List<Doctor> search(Integer hosId, Integer officeId) {
        return doctorService.findByHosIdAndOfficeId(hosId, officeId);
    }

    /**
     * 医生条件查询
     *
     * @param pageQuery 页面查询
     * @return {@link IPage}<{@link Doctor}>
     */
    @PostMapping("/allDoctor")
    public IPage<Doctor> allDoctor(@RequestBody DoctorPageQuery pageQuery) {
        return doctorService.page(new Page<>(pageQuery.getPageIndex(), pageQuery.getPageSize()), Wrappers.lambdaQuery(Doctor.class)
                        // 医生职称
                        .eq(pageQuery.getDoctorTitle() != null, Doctor::getDoctorTitle, pageQuery.getDoctorTitle())
                        // 行政职位
                        .eq(pageQuery.getDoctorAdministrative() != null, Doctor::getDoctorAdministrative, pageQuery.getDoctorAdministrative())
                        // 学位
                        .eq(pageQuery.getDoctorDegree() != null, Doctor::getDoctorDegree, pageQuery.getDoctorDegree())
                        // 医生姓名 前缀模糊查询
                        .likeRight(pageQuery.getDoctorName() != null, Doctor::getDoctorName, pageQuery.getDoctorName())
                        .eq(pageQuery.getHosId() != null, Doctor::getHosId, pageQuery.getHosId())
                        .eq(pageQuery.getOfficeId() != null, Doctor::getOfficeId, pageQuery.getOfficeId())
                        // 根据id倒序排序
                        .orderByDesc(Doctor::getId))
                .convert(doctor -> {
                    // 医院名称
                    doctor.setHospitalName(hospitalService.findNameById(doctor.getHosId()));
                    // 科室名称
                    doctor.setOfficesName(officeService.findNameById(doctor.getOfficeId()));
                    return doctor;
                });
    }

    /**
     * 添加医生
     *
     * @param doctor 医生
     */
    @PostMapping("add")
    public void add(@RequestBody Doctor doctor) {
        doctorService.save(doctor);
    }

    /**
     * 更新医生
     *
     * @param doctor 医生
     */
    @PutMapping("update")
    public void update(@RequestBody Doctor doctor) {
        doctorService.updateById(doctor);
    }

    /**
     * 删除医生
     *
     * @param id id
     */
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        doctorService.delete(id);
    }
}
