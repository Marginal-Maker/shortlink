package com.majing.shortlink.project.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author majing
 * @date 2024-04-17 15:06
 * @Description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkCreateRespDto {

    private String fullShortUrl; // 完整短链接

    private String originUrl; // 原始链接

    private String gid; // 分组标识

    private String favicon;
}
