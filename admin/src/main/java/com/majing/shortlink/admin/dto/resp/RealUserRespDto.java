package com.majing.shortlink.admin.dto.resp;

import lombok.Data;

/**
 * @author majing
 * @date 2024-04-08 18:37
 * @Description 用户响应参数实体（未脱敏）
 */
@Data
public class RealUserRespDto {
    // ID
    private Long id;
    // 用户名
    private String username;
    // 真实姓名
    private String realName;
    // 手机号
    private String phone;
    // 邮箱
    private String mail;
}
