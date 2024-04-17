package com.majing.shortlink.admin.common.biz.user;


import com.alibaba.fastjson2.JSON;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;

import static com.majing.shortlink.admin.common.constant.RedisCacheConstant.USER_LOGIN_KEY;

/**
 * @author majing
 * @date 2024-04-15 16:38
 * @Description 用户传输信息过滤器
 */
@RequiredArgsConstructor
public class UserTransmitFilter implements Filter {
    private final StringRedisTemplate stringRedisTemplate;
    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String username = httpServletRequest.getHeader("username");
        String token = httpServletRequest.getHeader("token");
        Object userInfoJsonStr = stringRedisTemplate.opsForHash().get(USER_LOGIN_KEY+username, token);
        if(userInfoJsonStr!=null){
            UserInfoDto userInfoDto = JSON.parseObject(userInfoJsonStr.toString(), UserInfoDto.class);
            UserContext.setUser(userInfoDto);
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        }finally {
            UserContext.removeUser();
        }
    }
}
