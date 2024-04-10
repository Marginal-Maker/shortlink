package com.majing.shortlink.admin.dto.req;

import lombok.Data;

/**
 * @author majing
 * @date 2024-04-08 18:37
 * @Description 用户注册请求参数实体
 */
@Data
public class UserRegisterReqDto {
    // 用户名
    private String username;
    // 密码
    private String password;
    // 真实姓名
    private String realName;
    // 手机号
    private String phone;
    // 邮箱
    private String mail;
}
