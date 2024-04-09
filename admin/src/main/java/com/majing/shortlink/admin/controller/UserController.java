package com.majing.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.majing.shortlink.admin.common.convention.result.Result;
import com.majing.shortlink.admin.common.convention.result.Results;
import com.majing.shortlink.admin.dto.req.UserRegisterRespDto;
import com.majing.shortlink.admin.dto.resp.RealUserRespDto;
import com.majing.shortlink.admin.dto.resp.UserRespDto;
import com.majing.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author majing
 * @date 2024-04-08 18:33
 * @Description 用户控制层
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/short-link/v1/user")
public class UserController {
    private final UserService userService;
    /**
     * 该方法获取的字段会进行脱敏处理
     * @created at 2024/4/9 15:07
    */
    @GetMapping("/{username}")
    public Result<UserRespDto> getUserByUsername(@PathVariable("username") String username){
        return Results.success(userService.getUserByUsername(username));
    }
    /**
     * 该方法获取真实的用户信息
     * @created at 2024/4/9 15:08
    */
    @GetMapping("/real/{username}")
    public Result<RealUserRespDto> getRealUserByUsername(@PathVariable("username") String username){
        //将userRespDto对象转化为RealUserRespDto类对应的对象，这样Springboot在进行序列化时检测不到相应注解就不会进行特殊处理
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username), RealUserRespDto.class));
    }
    /**
     * 查询用户名是否存在
     * @created at 2024/4/9 16:03
    */
    @GetMapping("/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username){
        return Results.success(userService.hasUsername(username));
    }

    /**
     * 用户注册
     * @created at 2024/4/9 17:09
    */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody UserRegisterRespDto userRegisterRespDto){
        userService.register(userRegisterRespDto);
        return Results.success();
    }
}
