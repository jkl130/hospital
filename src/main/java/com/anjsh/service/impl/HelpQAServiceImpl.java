package com.anjsh.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjsh.dao.HelpQADao;
import com.anjsh.entity.HelpQA;
import com.anjsh.service.HelpQAService;

@Service
public class HelpQAServiceImpl extends ServiceImpl<HelpQADao, HelpQA> implements HelpQAService {
    @Autowired
    private HelpQADao helpQADao;

    @Override
    public List<HelpQA> findQAByType(String questionType) {
        // TODO Auto-generated method stub
        return helpQADao.findQAByType(questionType);
    }

}
