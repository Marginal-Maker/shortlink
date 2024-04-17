package com.majing.shortlink.admin.dto.req;

import lombok.Data;

/**
 * @author majing
 * @date 2024-04-15 19:45
 * @Description 分组更新
 */
@Data
public class GroupUpdateReqDto {
    private String gid; // 分组标识
    private String name; // 分组名称
}
