package com.majing.shortlink.admin.controller;

import com.majing.shortlink.admin.common.convention.result.Result;
import com.majing.shortlink.admin.common.convention.result.Results;
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
    @GetMapping("/user/{username}")
    public Result<UserRespDto> getUserByUsername(@PathVariable("username") String username){
        return Results.success(userService.getUserByUsername(username));
    }
}
