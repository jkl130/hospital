package com.wly.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wly.entity.Comment;

/**
 * 评论服务
 *
 * @author anjsh
 * @date 2021/10/27 15:40
 */
public interface CommentService extends IService<Comment> {
    /**
     * 回答
     *
     * @param comment 评论
     */
    void answer(Comment comment);
}
