package com.majing.shortlink.admin.dto.resp;

import lombok.Data;

/**
 * @author majing
 * @date 2024-04-15 16:02
 * @Description 短连接响应实体
 */
@Data
public class GroupRespDto {
    private Long id; // ID
    private String gid; // 分组标识
    private String name; // 分组名称
    private String username; // 创建分组用户名
    private Integer sortOrder; // 分组排序
}
