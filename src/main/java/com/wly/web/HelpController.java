package com.wly.web;

import com.wly.entity.FeedBack;
import com.wly.entity.HelpQA;
import com.wly.mapper.FeedBackMapper;
import com.wly.service.CommonUserService;
import com.wly.service.HelpQAService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 帮助控制器
 *
 * @author wly
 * @date 2021/10/27
 */
@RestController
@RequestMapping("help")
public class HelpController {

    @Resource
    private HelpQAService helpQAService;

    @Resource
    private FeedBackMapper feedBackMapper;

    @Resource
    private CommonUserService commonUserService;


    /**
     * 列出反馈
     *
     * @return {@link List}<{@link FeedBack}>
     */
    @GetMapping("listFd")
    private List<FeedBack> listFd() {
        return feedBackMapper.selectList(null).stream().peek(feedBack -> feedBack.setUserName(commonUserService.getUserNameById(feedBack.getUserId()))).collect(Collectors.toList());
    }

    /**
     * 列出问答
     *
     * @return {@link List}<{@link HelpQA}>
     */
    @GetMapping("/list")
    public List<HelpQA> list() {
        return helpQAService.list();
    }

    /**
     * 添加问答
     *
     * @param qa 质量保证
     */
    @PostMapping("add")
    public void add(@RequestBody HelpQA qa) {
        helpQAService.save(qa);
    }

    /**
     * 更新问答
     *
     * @param qa 质量保证
     */
    @PutMapping("update")
    public void update(@RequestBody HelpQA qa) {
        helpQAService.updateById(qa);
    }

    /**
     * 删除问答
     *
     * @param id id
     */
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        helpQAService.removeById(id);
    }
}
