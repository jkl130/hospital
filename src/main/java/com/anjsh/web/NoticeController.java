package com.anjsh.web;

import java.util.List;

import com.anjsh.entity.HelpQA;
import com.anjsh.entity.Notice;
import com.anjsh.service.NoticeService;
import com.anjsh.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author sfturing
 * @date 2017年6月2日
 */
@RestController
@RequestMapping("notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @GetMapping("/list")
    public List<Notice> list() {
        return noticeService.list();
    }

    @PostMapping("add")
    public void add(@RequestBody Notice notice) {
        noticeService.save(notice);
    }

    @PutMapping("update")
    public void update(@RequestBody Notice notice) {
        noticeService.updateById(notice);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        noticeService.removeById(id);
    }
}
