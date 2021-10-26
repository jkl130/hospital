package com.anjsh.web;

import cn.hutool.core.lang.Assert;
import com.anjsh.entity.Doctor;
import com.anjsh.entity.Office;
import com.anjsh.entity.OrderRecords;
import com.anjsh.service.DoctorService;
import com.anjsh.service.HospitalService;
import com.anjsh.service.OfficeService;
import com.anjsh.service.OrderRecordsService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sfturing
 * @date 2017年6月2日
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

    @GetMapping("list")
    private List<OrderRecords> list() {
        return orderRecordsService.list();
    }

    @PostMapping("add")
    public void add(@RequestBody OrderRecords records) {
        Integer hosId = Assert.notNull(hospitalService.findHosByName(records.getHospitalName())).getId();
        records.setHosId(hosId);
        records.setDoctorId(Assert.notNull(doctorService.getOne(Wrappers.lambdaQuery(Doctor.class).eq(Doctor::getHosId, hosId).eq(Doctor::getOfficeId, Assert.notNull(officeService.getOne(Wrappers.lambdaQuery(Office.class).eq(Office::getHosId, hosId).eq(Office::getOfficesName, records.getOfficesName()))).getId()).eq(Doctor::getDoctorName, records.getDoctorName()))).getId());
        orderRecordsService.save(records);
    }

    @PutMapping("update")
    public void update(@RequestBody OrderRecords records) {
        Integer hosId = Assert.notNull(hospitalService.findHosByName(records.getHospitalName())).getId();
        records.setHosId(hosId);
        records.setDoctorId(Assert.notNull(doctorService.getOne(Wrappers.lambdaQuery(Doctor.class).eq(Doctor::getHosId, hosId).eq(Doctor::getOfficeId, Assert.notNull(officeService.getOne(Wrappers.lambdaQuery(Office.class).eq(Office::getHosId, hosId).eq(Office::getOfficesName, records.getOfficesName()))).getId()).eq(Doctor::getDoctorName, records.getDoctorName()))).getId());
        orderRecordsService.updateById(records);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        orderRecordsService.removeById(id);
    }

}
