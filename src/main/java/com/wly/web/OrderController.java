package com.wly.web;

import com.wly.entity.Doctor;
import com.wly.entity.Office;
import com.wly.entity.OrderRecords;
import com.wly.exception.Assert;
import com.wly.service.DoctorService;
import com.wly.service.HospitalService;
import com.wly.service.OfficeService;
import com.wly.service.OrderRecordsService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 订单控制器
 *
 * @author wly
 * @date 2021/10/27
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderRecordsService orderRecordsService;

    @Resource
    private HospitalService hospitalService;

    @Resource
    private DoctorService doctorService;

    @Resource
    private OfficeService officeService;

    /**
     * 列出订单
     *
     * @return {@link List}<{@link OrderRecords}>
     */
    @GetMapping("list")
    private List<OrderRecords> list() {
        return orderRecordsService.list();
    }

    /**
     * 添加
     *
     * @param records 订单
     */
    @PostMapping("add")
    public void add(@RequestBody OrderRecords records) {
        setOrder(records);
        orderRecordsService.save(records);
    }

    /**
     * 设置订单
     *
     * @param records 记录
     */
    private void setOrder(@RequestBody OrderRecords records) {
        Integer hosId = hospitalService.findHosByName(records.getHospitalName()).getId();
        // 医院id
        records.setHosId(hosId);
        // 医生id
        records.setDoctorId(Assert.notNull(doctorService.getOne(Wrappers.lambdaQuery(Doctor.class).eq(Doctor::getHosId, hosId).eq(Doctor::getOfficeId, Assert.notNull(officeService.getOne(Wrappers.lambdaQuery(Office.class).eq(Office::getHosId, hosId).eq(Office::getOfficesName, records.getOfficesName())), "科室" + records.getOfficesName() + "不存在").getId()).eq(Doctor::getDoctorName, records.getDoctorName())), "医生" + records.getDoctorName() + "不存在").getId());
    }

    /**
     * 更新
     *
     * @param records 订单
     */
    @PutMapping("update")
    public void update(@RequestBody OrderRecords records) {
        setOrder(records);
        orderRecordsService.updateById(records);
    }

    /**
     * 删除
     *
     * @param id id
     */
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        orderRecordsService.removeById(id);
    }

}
