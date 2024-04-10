package com.majing.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.majing.shortlink.admin.dao.entity.UserDO;
import com.majing.shortlink.admin.dto.req.UserLoginReqDto;
import com.majing.shortlink.admin.dto.req.UserRegisterReqDto;
import com.majing.shortlink.admin.dto.req.UserUpdateReqDto;
import com.majing.shortlink.admin.dto.resp.UserLoginRespDto;
import com.majing.shortlink.admin.dto.resp.UserRespDto;

/**
 * @author majing
 * @date 2024-04-08 18:29
 * @Description 用户业务层
 */
public interface UserService extends IService<UserDO>{
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return com.majing.shortlink.admin.dto.resp.UserRespDto
     * @created at 2024/4/8 18:41
    */
    UserRespDto getUserByUsername(String username);
    /**
     * 查询用户名是否已经存在
     * @param username
     * @return java.lang.Boolean
     * @created at 2024/4/9 15:59
    */
    Boolean hasUsername(String username);
    /**
     * 用户注册功能
     * @param userRegisterReqDto
     * @return void
     * @created at 2024/4/9 16:25
    */
    void register(UserRegisterReqDto userRegisterReqDto);
    /**
     * 更新用户信息
     * @param userUpdateReqDto
     * @return void
     * @created at 2024/4/10 13:48
    */
    void update(UserUpdateReqDto userUpdateReqDto);
    /**
     * 用户登录
     *
     * @param userLoginReqDto
     * @return java.lang.String
     * @created at 2024/4/10 14:50
     */
    UserLoginRespDto login(UserLoginReqDto userLoginReqDto);
    /**
     * 验证用户是否已经登录
     * @param token
     * @return java.lang.Boolean
     * @created at 2024/4/10 15:21
    */
    Boolean checkLogin(String username, String token);
    /**
     * 用户退登
     * @param username
     * @return void
     * @created at 2024/4/10 16:09
    */
    void logout(String username, String token);
}
