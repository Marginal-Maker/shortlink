package com.majing.shortlink.admin.common.database;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @author majing
 * @date 2024-04-15 15:50
 * @Description 数据表通用字段
 */
@Data
public class BaseDO {
    @TableField(fill = FieldFill.INSERT)
    private Date createTime; // 创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime; // 修改时间
    @TableField(fill = FieldFill.INSERT)
    private Integer delFlag; // 删除标识 0：未删除 1：已删除
}
