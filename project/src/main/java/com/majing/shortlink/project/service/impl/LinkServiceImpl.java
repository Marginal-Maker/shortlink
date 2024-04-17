package com.majing.shortlink.project.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.StrBuilder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.majing.shortlink.project.commom.convention.exception.ServiceException;
import com.majing.shortlink.project.dao.entity.LinkDO;
import com.majing.shortlink.project.dao.mapper.LinkMapper;
import com.majing.shortlink.project.dto.req.LinkCreateReqDto;
import com.majing.shortlink.project.dto.resp.LinkCreateRespDto;
import com.majing.shortlink.project.service.LinkService;
import com.majing.shortlink.project.util.HashUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * @author majing
 * @date 2024-04-17 14:40
 * @Description 短连接接口实现层
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LinkServiceImpl extends ServiceImpl<LinkMapper, LinkDO> implements LinkService {
    private final RBloomFilter<String> shortUriCreateCachePenetrationBloomFilter;
    public LinkCreateRespDto createShortLink(LinkCreateReqDto linkCreateReqDto) {
        String shortLinkSuffix = generateShortLink(linkCreateReqDto);
        String fullShortLink = StrBuilder.create(linkCreateReqDto.getDomain())
                .append("/")
                .append(shortLinkSuffix)
                .toString();
        LinkDO linkDO = LinkDO.builder()
                .domain(linkCreateReqDto.getDomain())
                .originUrl(linkCreateReqDto.getOriginUrl())
                .gid(linkCreateReqDto.getGid())
                .createdType(linkCreateReqDto.getCreatedType())
                .validDateType(linkCreateReqDto.getValidDateType())
                .validDate(linkCreateReqDto.getValidDate())
                .describe(linkCreateReqDto.getDescribe())
                .shortUri(shortLinkSuffix)
                .enableStatus(1)
                .fullShortUrl(fullShortLink)
                .build();
        try{
            baseMapper.insert(linkDO);
        }catch (DuplicateKeyException ex){
            log.warn("短连接：{}重复入库", fullShortLink);
            throw  new ServiceException("短链接生成重复。");
        }
        shortUriCreateCachePenetrationBloomFilter.add(fullShortLink);
        return LinkCreateRespDto.builder()
                .gid(linkCreateReqDto.getGid())
                .fullShortUrl(fullShortLink)
                .originUrl(linkCreateReqDto.getOriginUrl())
                .build();
    }

    private String generateShortLink(LinkCreateReqDto linkCreateReqDto){
        int max = 0;
        String shortUri;
        while(true){
            if(max>10){
                throw new ServiceException("短链接频繁生产，请稍后再试");
            }
            String originUrl = linkCreateReqDto.getOriginUrl();
            originUrl += UUID.randomUUID().toString();
            shortUri = HashUtil.hashToBase62(originUrl);
            if(!shortUriCreateCachePenetrationBloomFilter.contains(linkCreateReqDto.getDomain() + "/" + shortUri)){
                break;
            }
            max++;
        }
        return shortUri;
    }
}
