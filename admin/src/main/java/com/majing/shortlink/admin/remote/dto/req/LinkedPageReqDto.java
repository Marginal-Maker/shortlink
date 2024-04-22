package com.majing.shortlink.admin.remote.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author majing
 * @date 2024-04-18 15:07
 * @Description 短链接分页请求参数
 */
@Data
public class LinkedPageReqDto extends Page {
    private String gid;
}
