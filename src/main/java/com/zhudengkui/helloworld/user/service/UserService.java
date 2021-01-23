package com.zhudengkui.helloworld.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhudengkui.helloworld.user.entity.User;
import com.zhudengkui.helloworld.user.entity.UserVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zdk
 * @since 2020-08-01
 */
public interface UserService extends IService<User> {
    
    /**
     * 方法描述: 分页查询
     * @param userVo
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.zhudengkui.helloworld.user.entity.User>
     * @author zdk
     * <br/><b>创建时间:</b>2021/1/23 18:53
     * <br/><b>修 改 人:</b>zdk
     * <br/><b>修改时间:</b>2021/1/23 18:53
     * @since  1.0.0
     */
    Page<User> pageUserByParam(UserVo userVo);
    
    /**
     * 方法描述: 统计数据总量
     * @param userVo 请求参数
     * @return java.lang.Integer
     * @author zdk
     * <br/><b>创建时间:</b>2021/1/23 18:53
     * <br/><b>修 改 人:</b>zdk
     * <br/><b>修改时间:</b>2021/1/23 18:53
     * @since  1.0.0
     */
    @SuppressWarnings("unused")
    Integer countUserByPage(UserVo userVo);
    
}
