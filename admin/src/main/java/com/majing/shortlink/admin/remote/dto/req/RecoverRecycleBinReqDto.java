package com.majing.shortlink.admin.remote.dto.req;

import lombok.Data;

/**
 * @author majing
 * @date 2024-05-02 18:29
 * @Description
 */
@Data
public class RecoverRecycleBinReqDto {
    String gid;
    String fullShortUrl;
}
