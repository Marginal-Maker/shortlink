package com.majing.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.majing.shortlink.project.dao.entity.LinkDO;
import com.majing.shortlink.project.dto.req.LinkCreateReqDto;
import com.majing.shortlink.project.dto.req.LinkedPageReqDto;
import com.majing.shortlink.project.dto.resp.LinkCountRespDto;
import com.majing.shortlink.project.dto.resp.LinkCreateRespDto;
import com.majing.shortlink.project.dto.resp.LinkedPageRespDto;

import java.util.List;

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

    /**
     * 分页
     * @param linkedPageReqDto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.majing.shortlink.project.dto.resp.LinkedPageRespDto>
     * @created at 2024/4/18 15:09
    */
    IPage<LinkedPageRespDto> pageLink(LinkedPageReqDto linkedPageReqDto);
    /**
     * 按分组查询短链接数量
     * @param gidList
     * @return java.util.List<com.majing.shortlink.project.dto.resp.LinkCountRespDto>
     * @created at 2024/4/22 16:36
    */
    List<LinkCountRespDto> listGroupLinkCount(List<String> gidList);
}
