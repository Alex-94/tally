package com.alex.top.tally.service.impl;

import org.springframework.stereotype.Service;

import com.alex.top.tally.dao.AlexUserDao;
import com.alex.top.tally.entity.AlexUserEntity;
import com.alex.top.tally.service.AlexUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

@Service("alexUserService")
public class AlexUserServiceImpl extends ServiceImpl<AlexUserDao, AlexUserEntity> implements AlexUserService {

}
