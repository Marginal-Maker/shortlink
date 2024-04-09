package com.majing.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.majing.shortlink.admin.common.convention.result.Result;
import com.majing.shortlink.admin.common.convention.result.Results;
import com.majing.shortlink.admin.dto.resp.RealUserRespDto;
import com.majing.shortlink.admin.dto.resp.UserRespDto;
import com.majing.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author majing
 * @date 2024-04-08 18:33
 * @Description
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/shortlink/v1")
public class UserController {
    private final UserService userService;
    /**
     * 该方法获取的字段会进行脱敏处理
     * @created at 2024/4/9 15:07
    */
    @GetMapping("/user/{username}")
    public Result<UserRespDto> getUserByUsername(@PathVariable("username") String username){
        return Results.success(userService.getUserByUsername(username));
    }
    /**
     * 该方法获取真实的用户信息
     * @created at 2024/4/9 15:08
    */
    @GetMapping("/realUser/{username}")
    public Result<RealUserRespDto> getRealUserByUsername(@PathVariable("username") String username){
        //将userRespDto对象转化为RealUserRespDto类对应的对象，这样Springboot在进行序列化时检测不到相应注解就不会进行特殊处理
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username), RealUserRespDto.class));
    }
}
