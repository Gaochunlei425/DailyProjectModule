package com.company.project.service.impl;

import com.company.project.mapper.UserTestMapper;
import com.company.project.model.UserTest;
import com.company.project.service.UserTestService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020/12/08.
 */
@Service
@Transactional
public class UserTestServiceImpl extends AbstractService<UserTest> implements UserTestService {
    @Resource
    private UserTestMapper userTestMapper;

}
