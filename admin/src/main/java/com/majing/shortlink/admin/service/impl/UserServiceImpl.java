package com.majing.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.majing.shortlink.admin.dao.entity.UserDO;
import com.majing.shortlink.admin.dao.mapper.UserMapper;
import com.majing.shortlink.admin.dto.resp.UserRespDto;
import com.majing.shortlink.admin.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author majing
 * @date 2024-04-08 18:31
 * @Description 用户业务层接口实现
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    @Override
    public UserRespDto getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        UserRespDto userRespDto = new UserRespDto();
        BeanUtils.copyProperties(userDO,userRespDto);
        return userRespDto;
    }
}
