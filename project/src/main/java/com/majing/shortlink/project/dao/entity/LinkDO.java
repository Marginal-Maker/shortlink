package com.majing.shortlink.project.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.majing.shortlink.project.commom.database.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author majing
 * @date 2024-04-17 14:31
 * @Description 短连接持久层实体
 */
@Data
@TableName("t_link")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long id; // ID

    // @Column 表示这是数据库表中的一个字段
    private String domain; // 域名

    private String shortUri; // 短链接

    private String fullShortUrl; // 完整短链接

    // @Column(length = 1024) 指定了字段的长度
    private String originUrl; // 原始链接

    private Integer clickNum; // 点击量

    private String gid; // 分组标识

    private Integer enableStatus; // 启用标识 0：未启用 1：已启用

    private Integer createdType; // 创建类型 0：控制台 1：接口

    private Integer validDateType; // 有效期类型 0：永久有效 1：用户自定义

    private Date validDate; // 有效期

    // @Column(length = 1024) 指定了字段的长度
    @TableField("`describe`")
    private String describe; // 描述
}
