package com.wly.web;

import com.wly.entity.Notice;
import com.wly.service.NoticeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 公告控制器
 *
 * @author DPP-1
 * @date 2021/10/27
 */
@RestController
@RequestMapping("notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    /**
     * 列表
     *
     * @return {@link List}<{@link Notice}>
     */
    @GetMapping("/list")
    public List<Notice> list() {
        return noticeService.list();
    }

    /**
     * 添加
     *
     * @param notice 公告
     */
    @PostMapping("add")
    public void add(@RequestBody Notice notice) {
        noticeService.save(notice);
    }

    /**
     * 更新
     *
     * @param notice 公告
     */
    @PutMapping("update")
    public void update(@RequestBody Notice notice) {
        noticeService.updateById(notice);
    }

    /**
     * 删除
     *
     * @param id id
     */
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        noticeService.removeById(id);
    }
}
