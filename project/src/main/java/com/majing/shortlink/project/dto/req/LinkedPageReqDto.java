package com.majing.shortlink.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.majing.shortlink.project.dao.entity.LinkDO;
import lombok.Data;

/**
 * @author majing
 * @date 2024-04-18 15:07
 * @Description 短链接分页请求参数
 */
@Data
public class LinkedPageReqDto extends Page<LinkDO> {
    private String gid;
}
