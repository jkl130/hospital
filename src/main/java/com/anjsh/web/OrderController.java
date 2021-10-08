package com.anjsh.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.anjsh.dto.OrderOfficePageQuery;
import com.anjsh.entity.*;
import com.anjsh.service.CommonUserService;
import com.anjsh.service.HospitalService;
import com.anjsh.service.OrderRecordsService;
import com.anjsh.utils.UserContext;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.anjsh.dao.FavouriteDao;

/**
 * @author sfturing
 * @date 2017年6月2日
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderRecordsService orderRecordsService;

    @GetMapping("list")
    private List<OrderRecords> list() {
        return orderRecordsService.list();
    }

    @PostMapping("add")
    public void add(@RequestBody OrderRecords records) {
        orderRecordsService.save(records);
    }

    @PutMapping("update")
    public void update(@RequestBody OrderRecords records) {
        orderRecordsService.updateById(records);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        orderRecordsService.removeById(id);
    }

}
