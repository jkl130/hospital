package com.wly.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wly.dto.HosPageQuery;
import com.wly.entity.Hospital;
import com.wly.entity.Office;
import com.wly.entity.OrderRecords;
import com.wly.service.DoctorService;
import com.wly.service.HospitalService;
import com.wly.service.OfficeService;
import com.wly.service.OrderRecordsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 医院控制器
 *
 * @author wly
 * @date 2021/10/27
 */
@RestController
@RequestMapping("hos")
public class HospitalController {

    @Resource
    private HospitalService hospitalService;

    @Resource
    private OfficeService officeService;

    @Resource
    private OrderRecordsService orderRecordsService;

    @Resource
    private DoctorService doctorService;

    @GetMapping("search")
    public List<Hospital> search(String hospitalName) {
        return hospitalService.findByName(hospitalName);
    }

    /**
     * 医院条件查询
     *
     * @param pageQuery 页面查询
     * @return {@link IPage}<{@link Hospital}>
     */
    @PostMapping("/orderHos")
    public IPage<Hospital> orderHos(@RequestBody HosPageQuery pageQuery) {
        // 今年第一天
        LocalDateTime startOfYear = LocalDateTime.of(LocalDateTime.now().getYear(), 1, 1, 0, 0);
        return hospitalService.page(new Page<>(pageQuery.getPageIndex(), pageQuery.getPageSize()), Wrappers.lambdaQuery(Hospital.class)
                        // 医院类型
                        .eq(pageQuery.getHospitalNature() != null, Hospital::getHospitalNature, pageQuery.getHospitalNature())
                        // 医院等级
                        .eq(pageQuery.getHospitalGrade() != null, Hospital::getHospitalGrade, pageQuery.getHospitalGrade())
                        // 医院名称 前缀模糊查询
                        .likeRight(pageQuery.getHospitalName() != null, Hospital::getHospitalName, pageQuery.getHospitalName())
                        // 指定省查询医院地址 前缀模糊查询
                        .likeRight(pageQuery.getProvince() != null, Hospital::getHospitalAddress, pageQuery.getProvince())
                        // 指定市查询医院地址 前缀模糊查询
                        .like(pageQuery.getCity() != null, Hospital::getHospitalAddress, pageQuery.getCity())
                        // 区
                        .eq(pageQuery.getDistrict() != null, Hospital::getHospitalArea, pageQuery.getDistrict())
                        // 根据id倒序排序
                        .orderByDesc(Hospital::getId))
                .convert(hospital -> {
                    // 科室数量
                    hospital.setHospitalOfficesNum(Math.toIntExact(officeService.count(Wrappers.lambdaQuery(Office.class).eq(Office::getHosId, hospital.getId()))));
                    // 年门诊量 = 医院的订单数量
                    hospital.setOutpatientNum(Math.toIntExact(orderRecordsService.count(Wrappers.lambdaQuery(OrderRecords.class)
                            .eq(OrderRecords::getHosId, hospital.getId())
                            // 已经完成
                            .eq(OrderRecords::getIsFinish, 1)
                            // 创建时间在今年第一天之后
                            .gt(OrderRecords::getCreateTime, startOfYear)
                    )));
                    return hospital;
                });
    }

    /**
     * 添加
     *
     * @param hospital 医院
     */
    @PostMapping("add")
    public void add(@RequestBody Hospital hospital) {
        hospitalService.save(hospital);
    }

    /**
     * 更新
     *
     * @param hospital 医院
     */
    @PutMapping("update")
    public void update(@RequestBody Hospital hospital) {
        hospitalService.updateById(hospital);
    }

    /**
     * 删除
     *
     * @param id id
     */
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        hospitalService.delete(id);
    }

}
