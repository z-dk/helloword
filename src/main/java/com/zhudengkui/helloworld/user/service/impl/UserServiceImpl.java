package com.zhudengkui.helloworld.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhudengkui.helloworld.user.entity.User;
import com.zhudengkui.helloworld.user.entity.UserVo;
import com.zhudengkui.helloworld.user.mapper.UserMapper;
import com.zhudengkui.helloworld.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zdk
 * @since 2020-08-01
 */
@Service
@Scope("myThreadScope")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Override
    public Page<User> pageUserByParam(UserVo userVo){
        QueryWrapper<User> wrapper = new QueryWrapper<>(userVo.getUser());
        Page<User> page = new Page<>();
        if (userVo.getPagingFlag()) {
            page.setPages(userVo.getPage());
            page.setSize(userVo.getRows());
            return page(page, wrapper);
        }
        page.setRecords(list(wrapper));
        return page;
    }
    
    @Cacheable(value = "user", key = "#id")
    @Override
    public User getUserById(String id) {
        LOGGER.info("通过用户id【{}】从Mysql查询用户信息", id);
        return getById(id);
    }
    
    @Async
    @Override
    public CompletableFuture<Integer> countUserByPage(UserVo userVo){
        QueryWrapper<User> wrapper = new QueryWrapper<>(userVo.getUser());
        return CompletableFuture.completedFuture(count(wrapper));
    }
    
    
}
