package com.anjsh.web;

import com.anjsh.dao.CommentDao;
import com.anjsh.entity.Comment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author anjsh
 * @date 2021/10/13 15:42
 */
@RestController
@RequestMapping("comment")
public class CommentController {

    @Resource
    private CommentDao commentDao;

    @GetMapping("list")
    public List<Comment> list() {
        return commentDao.selectList(null);
    }

    @PutMapping("update")
    public void update(@RequestBody Comment comment) {
        comment.setAtime(LocalDateTime.now());
        commentDao.updateById(comment);
    }
}
