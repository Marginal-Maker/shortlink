package com.majing.shortlink.admin.remote.dto.resp;

import lombok.Data;

/**
 * @author majing
 * @date 2024-04-22 16:12
 * @Description 按gid查询短链接参数响应实体
 */
@Data
public class LinkCountRespDto {
    String gid;
    Integer linkCount;
}
