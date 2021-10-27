package com.wly.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wly.dto.OfficePageQuery;
import com.wly.entity.Doctor;
import com.wly.entity.Hospital;
import com.wly.entity.Office;
import com.wly.service.DoctorService;
import com.wly.service.HospitalService;
import com.wly.service.OfficeService;
import com.wly.service.OrderRecordsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 科室控制器
 *
 * @author DPP-1
 * @date 2021/10/27
 */
@RestController
@RequestMapping("office")
public class OfficeController {

    @Resource
    private OfficeService officeService;

    @Resource
    private HospitalService hospitalService;

    @Resource
    private DoctorService doctorService;

    @Resource
    private OrderRecordsService orderRecordsService;

    @GetMapping("search")
    public List<Office> search(Integer hosId, String officeName) {
        return officeService.findByHosIdAndName(hosId, officeName);
    }

    /**
     * 科室条件查询
     *
     * @param pageQuery 页面查询
     * @return {@link IPage}<{@link Office}>
     */
    @PostMapping("/orderOffice")
    public IPage<Office> orderOffice(@RequestBody OfficePageQuery pageQuery) {
        // 查询条件
        LambdaQueryWrapper<Office> wrapper = Wrappers.lambdaQuery(Office.class)
                // 根据科室名称 前缀模糊查询
                .likeRight(pageQuery.getOfficesName() != null, Office::getOfficesName, pageQuery.getOfficesName())
                // 根据医院id和科室id倒序排序
                .orderByDesc(Arrays.asList(Office::getHosId, Office::getId));

        if (pageQuery.getHospitalName() != null) {
            // 根据医院名称 前缀模糊
            List<Integer> hosIds = hospitalService.findByName(pageQuery.getHospitalName()).stream().map(Hospital::getId).collect(Collectors.toList());
            if (hosIds.isEmpty()) {
                return new Page<>();
            }
            wrapper.in(Office::getHosId, hosIds);
        }

        return officeService.page(new Page<>(pageQuery.getPageIndex(), pageQuery.getPageSize()), wrapper)
                .convert(office -> {
                    // 医院名称
                    office.setHospitalName(hospitalService.findNameById(office.getHosId()));
                    // 医生人数
                    office.setDoctorNum(doctorService.count(Wrappers.lambdaQuery(Doctor.class).eq(Doctor::getHosId, office.getHosId()).eq(Doctor::getOfficeId, office.getId())) + "人");
                    return office;
                });
    }

    /**
     * 添加
     *
     * @param office 科室
     */
    @PostMapping("add")
    public void add(@RequestBody Office office) {
        officeService.save(office);
    }

    /**
     * 更新
     *
     * @param office 科室
     */
    @PutMapping("update")
    public void update(@RequestBody Office office) {
        officeService.updateById(office);
    }

    /**
     * 删除
     *
     * @param id id
     */
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        officeService.delete(id);
    }
}
