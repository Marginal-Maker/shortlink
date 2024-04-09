package com.majing.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.majing.shortlink.admin.dao.entity.UserDO;
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
}
