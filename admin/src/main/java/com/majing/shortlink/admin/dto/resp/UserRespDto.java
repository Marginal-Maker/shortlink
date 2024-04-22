package com.majing.shortlink.admin.dto.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.majing.shortlink.admin.common.serialize.PhoneDesensitizationSerializer;
import lombok.Data;

/**
 * @author majing
 * @date 2024-04-08 18:37
 * @Description 用户响应参数实体（脱敏）
 */
@Data
public class UserRespDto {
    // ID
    private Long id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 真实姓名
    private String realName;
    // 手机号
    @JsonSerialize(using = PhoneDesensitizationSerializer.class)
    private String phone;
    // 邮箱
    private String mail;
}
