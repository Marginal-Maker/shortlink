package com.majing.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.majing.shortlink.admin.common.convention.exception.ClientException;
import com.majing.shortlink.admin.dao.entity.UserDO;
import com.majing.shortlink.admin.dao.mapper.UserMapper;
import com.majing.shortlink.admin.dto.req.UserLoginReqDto;
import com.majing.shortlink.admin.dto.req.UserRegisterReqDto;
import com.majing.shortlink.admin.dto.req.UserUpdateReqDto;
import com.majing.shortlink.admin.dto.resp.UserLoginRespDto;
import com.majing.shortlink.admin.dto.resp.UserRespDto;
import com.majing.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.majing.shortlink.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;
import static com.majing.shortlink.admin.common.constant.RedisCacheConstant.USER_LOGIN_KEY;
import static com.majing.shortlink.admin.common.enums.UserErrorCodeEnum.*;

/**
 * @author majing
 * @date 2024-04-08 18:31
 * @Description 用户业务层接口实现
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;
    @Override
    public UserRespDto getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        UserRespDto userRespDto = new UserRespDto();
        BeanUtils.copyProperties(userDO,userRespDto);
        return userRespDto;
    }

    @Override
    public Boolean hasUsername(String username) {
        return userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReqDto userRegisterReqDto) {
        if(hasUsername(userRegisterReqDto.getUsername())){
            throw new ClientException(USER_NAME_EXIST);
        }
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + userRegisterReqDto.getUsername());
        try{
            if(lock.tryLock()){
                try{
                    int inserted = baseMapper.insert(BeanUtil.toBean(userRegisterReqDto, UserDO.class));
                    if(inserted<1){
                        throw new ClientException(USER_SAVE_ERROR);
                    }
                    userRegisterCachePenetrationBloomFilter.add(userRegisterReqDto.getUsername());
                    return;
                }catch (DuplicateKeyException exception){
                    throw new ClientException(USER_EXIST.message(), exception, USER_EXIST);
                }
            }
            throw new ClientException(USER_NAME_EXIST);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void update(UserUpdateReqDto userUpdateReqDto) {
        // TODO 验证当前用户是否已经登录
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, userUpdateReqDto.getUsername());
        baseMapper.update(BeanUtil.toBean(userUpdateReqDto, UserDO.class), queryWrapper);
    }

    @Override
    public UserLoginRespDto login(UserLoginReqDto userLoginReqDto) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, userLoginReqDto.getUsername())
                .eq(UserDO::getDelFlag,0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if(userDO == null){
            throw new ClientException(USER_NULL);
        }
        if(!StringUtils.equals(userDO.getPassword(), userLoginReqDto.getPassword())){
            throw new ClientException("密码错误");
        }
        //直接已uuid为key存储恶意登录会导致多个不同的uuid对应同一个值
        String key = USER_LOGIN_KEY + userLoginReqDto.getUsername();
        Boolean hasLogin = stringRedisTemplate.hasKey(key);
        if(hasLogin != null && hasLogin){
            throw new ClientException("禁止重复登录");
        }
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForHash().put(key, uuid, JSON.toJSONString(userDO));
        stringRedisTemplate.expire(key, 30L, TimeUnit.DAYS);
        //stringRedisTemplate.opsForValue().set(uuid, JSON.toJSONString(userDO), 30L, TimeUnit.DAYS);
        return new UserLoginRespDto(uuid);
    }

    @Override
    public Boolean checkLogin(String username ,String token) {
        return stringRedisTemplate.opsForHash().hasKey(USER_LOGIN_KEY + username, token);
    }

    @Override
    public void logout(String username, String token) {
        if(checkLogin(username, token)){
            stringRedisTemplate.delete(USER_LOGIN_KEY+username);
            return;
        }
        throw new ClientException("用户Token不存在或用户未登录");
    }
}
