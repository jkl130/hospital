package com.wly.web;

import com.wly.entity.Comment;
import com.wly.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 列出所有评论
     *
     * @return {@link List}<{@link Comment}>
     */
    @GetMapping("list")
    public List<Comment> list() {
        return commentService.list();
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
