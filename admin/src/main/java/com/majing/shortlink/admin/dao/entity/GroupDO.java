package com.majing.shortlink.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author majing
 * @date 2024-04-15 13:59
 * @Description
 */
@Data
@TableName("t_group")
public class GroupDO {
    @TableId(type = IdType.AUTO)
    private Long id; // ID
    private String gid; // 分组标识
    private String name; // 分组名称
    private String username; // 创建分组用户名
    private Integer sortOrder; // 分组排序
    @TableField(fill = FieldFill.INSERT)
    private Date createTime; // 创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime; // 修改时间
    private Integer delFlag; // 删除标识 0：未删除 1：已删除
}
