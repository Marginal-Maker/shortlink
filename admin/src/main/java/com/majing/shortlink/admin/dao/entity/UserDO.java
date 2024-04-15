package com.majing.shortlink.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.majing.shortlink.admin.common.database.BaseDO;
import lombok.Data;

/**
 * @author majing
 * @date 2024-04-08 18:19
 * @Description 用户持久层实体
 */
@Data
@TableName("t_user")
public class UserDO extends BaseDO {
    // ID
    @TableId(type = IdType.AUTO)
    private Long id;
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
    // 注销时间戳
    private Long deletionTime;

}
