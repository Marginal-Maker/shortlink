package com.majing.shortlink.admin.common.biz.user;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author majing
 * @date 2024-04-15 16:34
 * @Description 需要保存到当前线程中的用户信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDto {

    /**
     * 用户 ID
     */
    @JSONField(name = "id")
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 用户 Token
     */
    private String token;
}
