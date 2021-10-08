package com.anjsh.web;

import com.anjsh.dao.FeedBackDao;
import com.anjsh.entity.FeedBack;
import com.anjsh.entity.HelpQA;
import com.anjsh.service.HelpQAService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sfturing
 * @date 2017年6月1日
 */
@RestController
@RequestMapping("help")
public class HelpController {
    @Resource
    private HelpQAService helpQAService;

    @Resource
    private FeedBackDao feedBackDao;

    @GetMapping("listFd")
    private List<FeedBack> listFd() {
        return feedBackDao.selectList(null);
    }

    @GetMapping("/list")
    public List<HelpQA> list() {
        return helpQAService.list();
    }

    @PostMapping("add")
    public void add(@RequestBody HelpQA qa) {
        helpQAService.save(qa);
    }

    @PutMapping("update")
    public void update(@RequestBody HelpQA qa) {
        helpQAService.updateById(qa);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        helpQAService.removeById(id);
    }

}
