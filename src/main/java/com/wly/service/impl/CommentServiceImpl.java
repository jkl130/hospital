package com.wly.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.entity.Comment;
import com.wly.mapper.CommentMapper;
import com.wly.service.CommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/**
 * 评论实现类
 *
 * @author anjsh
 * @date 2021/10/27 15:40
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public void answer(Comment comment) {
        // 设置回复时间为当前时间
        comment.setAtime(LocalDateTimeUtil.formatNormal(LocalDateTime.now()));
        baseMapper.updateById(comment);
    }
}
