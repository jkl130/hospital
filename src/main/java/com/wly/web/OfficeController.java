package com.wly.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wly.dto.OfficePageQuery;
import com.wly.entity.Doctor;
import com.wly.entity.Hospital;
import com.wly.entity.Office;
import com.wly.entity.OrderRecords;
import com.wly.service.DoctorService;
import com.wly.service.HospitalService;
import com.wly.service.OfficeService;
import com.wly.service.OrderRecordsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
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
            wrapper.in(Office::getHosId, hospitalService.list(Wrappers.lambdaQuery(Hospital.class).likeRight(Hospital::getHospitalName, pageQuery.getHospitalName()).select(Hospital::getId)).stream().map(Hospital::getId).collect(Collectors.toList()));
        }

        return officeService.page(new Page<>(pageQuery.getPageIndex(), pageQuery.getPageSize()), wrapper)
                .convert(office -> {
                    // 医院名称
                    office.setHospitalName(hospitalService.getOne(Wrappers.lambdaQuery(Hospital.class).eq(Hospital::getId, office.getHosId()).select(Hospital::getHospitalName)).getHospitalName());
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
        setOffice(office);
        officeService.save(office);
    }

    /**
     * 设置科室的医院id
     *
     * @param office 科室
     */
    private void setOffice(@RequestBody Office office) {
        office.setHosId(hospitalService.findHosByName(office.getHospitalName()).getId());
    }

    /**
     * 更新
     *
     * @param office 科室
     */
    @PutMapping("update")
    public void update(@RequestBody Office office) {
        setOffice(office);
        officeService.updateById(office);
    }

    /**
     * 删除
     *
     * @param id id
     */
    @DeleteMapping("delete/{id}")
    @Transactional(rollbackFor = Exception.class)
    public void delete(@PathVariable Integer id) {
        List<Integer> doctorIds = doctorService.list(Wrappers.lambdaQuery(Doctor.class).eq(Doctor::getOfficeId, id).select(Doctor::getId)).stream().map(Doctor::getId).collect(Collectors.toList());
        if (!doctorIds.isEmpty()) {
            // 医生
            doctorService.removeByIds(doctorIds);
            // 订单
            orderRecordsService.remove(Wrappers.lambdaQuery(OrderRecords.class).in(OrderRecords::getDoctorId, doctorIds));
        }

        // 科室
        officeService.removeById(id);
    }
}
