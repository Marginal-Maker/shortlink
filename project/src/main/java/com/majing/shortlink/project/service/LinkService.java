package com.majing.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.majing.shortlink.project.dao.entity.LinkDO;
import com.majing.shortlink.project.dto.req.LinkCreateReqDto;
import com.majing.shortlink.project.dto.resp.LinkCreateRespDto;

/**
 * @author majing
 * @date 2024-04-17 14:39
 * @Description 短连接业务层
 */
public interface LinkService extends IService<LinkDO> {
    /**
     * 创建短链接
     * @param linkCreateReqDto
     * @return void
     * @created at 2024/4/17 15:08
    */
    LinkCreateRespDto createShortLink(LinkCreateReqDto linkCreateReqDto);
}
