package com.zhudengkui.helloworld.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhudengkui.helloworld.user.entity.User;
import com.zhudengkui.helloworld.user.entity.UserVo;
import com.zhudengkui.helloworld.user.mapper.UserMapper;
import com.zhudengkui.helloworld.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zdk
 * @since 2020-08-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    public List<User> listUserByPage(UserVo userVo){
        Page<User> page = new Page<>();
        
        QueryWrapper<User> wrapper = new QueryWrapper<>(userVo.getUser());
        Page<User> userPage = getBaseMapper().selectPage(page, wrapper);
        return userPage.getRecords();
    }
    
    @Override
    public Integer countUserByPage(UserVo userVo){
        QueryWrapper<User> wrapper = new QueryWrapper<>(userVo.getUser());
        return count(wrapper);
    }
    
    
}