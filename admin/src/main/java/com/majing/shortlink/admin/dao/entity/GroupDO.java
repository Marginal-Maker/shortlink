package com.majing.shortlink.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.majing.shortlink.admin.common.database.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author majing
 * @date 2024-04-15 13:59
 * @Description 分组实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_group")
public class GroupDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long id; // ID
    private String gid; // 分组标识
    private String name; // 分组名称
    private String username; // 创建分组用户名
    private Integer sortOrder; // 分组排序
}
