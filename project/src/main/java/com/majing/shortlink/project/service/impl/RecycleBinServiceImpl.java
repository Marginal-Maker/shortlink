package com.majing.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.majing.shortlink.project.dao.entity.LinkDO;
import com.majing.shortlink.project.dao.mapper.LinkMapper;
import com.majing.shortlink.project.dto.req.RecoverRecycleBinReqDto;
import com.majing.shortlink.project.dto.req.RecycleBinLinkPageReqDto;
import com.majing.shortlink.project.dto.req.SaveRecycleBinReqDto;
import com.majing.shortlink.project.dto.resp.LinkedPageRespDto;
import com.majing.shortlink.project.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static com.majing.shortlink.project.commom.constant.RedisKeyConstant.LINK_GOTO_KEY;
import static com.majing.shortlink.project.commom.constant.RedisKeyConstant.Link_ISNULL_KEY;

/**
 * @author majing
 * @date 2024-05-02 18:33
 * @Description
 */
@Service
@RequiredArgsConstructor
public class RecycleBinServiceImpl extends ServiceImpl<LinkMapper, LinkDO> implements RecycleBinService {
    private final StringRedisTemplate stringRedisTemplate;
    @Override
    public void save(SaveRecycleBinReqDto saveRecycleBinReqDto) {
        LambdaUpdateWrapper<LinkDO> updateWrapper = Wrappers.lambdaUpdate(new LinkDO())
                .eq(LinkDO::getGid, saveRecycleBinReqDto.getGid())
                .eq(LinkDO::getFullShortUrl, saveRecycleBinReqDto.getFullShortUrl())
                .eq(LinkDO::getEnableStatus, 1)
                .eq(LinkDO::getDelFlag, 0);
        LinkDO newLink = LinkDO.builder()
                .enableStatus(0)
                .build();
        baseMapper.update(newLink, updateWrapper);
        stringRedisTemplate.delete(
                String.format(LINK_GOTO_KEY, saveRecycleBinReqDto.getFullShortUrl())
        );
    }

    @Override
    public IPage<LinkedPageRespDto> pageLink(RecycleBinLinkPageReqDto recycleBinLinkPageReqDto) {
        LambdaQueryWrapper<LinkDO> queryWrapper = Wrappers.lambdaQuery(LinkDO.class)
                .in(LinkDO::getGid, recycleBinLinkPageReqDto.getGidList())
                .eq(LinkDO::getDelFlag,0)
                .eq(LinkDO::getEnableStatus,0)
                .orderByDesc(LinkDO::getCreateTime);
        IPage<LinkDO> linkDOIPage = baseMapper.selectPage(recycleBinLinkPageReqDto, queryWrapper);
        return linkDOIPage.convert(each -> BeanUtil.toBean(each, LinkedPageRespDto.class));
    }

    @Override
    public void recover(RecoverRecycleBinReqDto recoverRecycleBinReqDto) {
        LambdaUpdateWrapper<LinkDO> updateWrapper = Wrappers.lambdaUpdate(new LinkDO())
                .eq(LinkDO::getGid, recoverRecycleBinReqDto.getGid())
                .eq(LinkDO::getFullShortUrl, recoverRecycleBinReqDto.getFullShortUrl())
                .eq(LinkDO::getEnableStatus,0)
                .eq(LinkDO::getDelFlag,0);
        LinkDO newLinkDo = LinkDO.builder()
                .enableStatus(1).build();
        baseMapper.update(newLinkDo, updateWrapper);
        stringRedisTemplate.delete(
                String.format(Link_ISNULL_KEY, recoverRecycleBinReqDto.getFullShortUrl())
        );
    }
}
