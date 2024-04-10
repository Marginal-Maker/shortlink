package com.majing.shortlink.admin.dto.resp;

import lombok.Data;

/**
 * @author majing
 * @date 2024-04-08 18:37
 * @Description 用户响应参数实体（脱敏）
 */
@Data
public class UserLoginRespDto {
    //token
    private String token;

    public UserLoginRespDto(String token) {
        this.token = token;
    }
}
