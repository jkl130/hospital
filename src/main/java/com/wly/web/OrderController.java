package com.wly.web;

import com.wly.entity.OrderRecords;
import com.wly.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


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

    @Resource
    private CommonUserService commonUserService;

    @Resource
    private UserService userService;


    /**
     * 列出订单
     *
     * @return {@link List}<{@link OrderRecords}>
     */
    @GetMapping("list")
    private List<OrderRecords> list() {
        return orderRecordsService.listByMap(userService.info2()).stream().peek(orderRecords -> {
            // 患者名称
            orderRecords.setUsername(commonUserService.getUserNameById(orderRecords.getUserID()));
            // 医院名称
            orderRecords.setHospitalName(hospitalService.findNameById(orderRecords.getHosId()));
            // 科室名称
            orderRecords.setOfficesName(officeService.findNameById(orderRecords.getOfficeId()));
            // 医生名称
            orderRecords.setDoctorName(doctorService.findNameById(orderRecords.getDoctorId()));
        }).collect(Collectors.toList());
    }

    /**
     * 添加
     *
     * @param records 订单
     */
    @PostMapping("add")
    public void add(@RequestBody OrderRecords records) {
        orderRecordsService.save(records);
    }


    /**
     * 更新
     *
     * @param records 订单
     */
    @PutMapping("update")
    public void update(@RequestBody OrderRecords records) {
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
