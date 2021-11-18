package com.wly.web;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wly.entity.Comment;
import com.wly.entity.Doctor;
import com.wly.service.CommentService;
import com.wly.service.CommonUserService;
import com.wly.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论控制器
 *
 * @author wly
 * @date 2021/10/27
 */
@RestController
@RequestMapping("comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private DoctorService doctorService;

    @Resource
    private CommonUserService commonUserService;

    /**
     * 列出所有评论
     *
     * @return {@link List}<{@link Comment}>
     */
    @GetMapping("list")
    public List<Comment> list(Integer hosId, Integer officeId, Integer doctorId) {
        List<Integer> list = new ArrayList<>();
        if (doctorId == null) {
            list.addAll(doctorService.list(Wrappers.lambdaQuery(Doctor.class)
                    .eq(hosId != null, Doctor::getHosId, hosId)
                    .eq(officeId != null, Doctor::getOfficeId, officeId)
                    .select(Doctor::getId,Doctor::getDoctorName)).stream().map(Doctor::getId).collect(Collectors.toList()));
        } else {
            list.add(doctorId);
        }

        return commentService.list(Wrappers.lambdaQuery(Comment.class).in(!list.isEmpty(), Comment::getDoctorId, list))
                .stream()
                .peek(comment -> {
                    // 患者名称
                    comment.setUsername(commonUserService.getUserNameById(comment.getUserId()));
                    // 医生名称
                    comment.setDoctorName(doctorService.findNameById(comment.getDoctorId()));
                })
                .collect(Collectors.toList());

    }

    /**
     * 回复评论
     *
     * @param comment 评论
     */
    @PutMapping("update")
    public void update(@RequestBody Comment comment) {
        commentService.answer(comment);
    }
}
