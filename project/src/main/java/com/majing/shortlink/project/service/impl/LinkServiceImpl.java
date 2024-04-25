package com.majing.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.majing.shortlink.project.commom.convention.exception.ClientException;
import com.majing.shortlink.project.commom.convention.exception.ServiceException;
import com.majing.shortlink.project.commom.enums.validDateTypeEnum;
import com.majing.shortlink.project.dao.entity.LinkDO;
import com.majing.shortlink.project.dao.entity.LinkGotoDo;
import com.majing.shortlink.project.dao.mapper.LinkGotoMapper;
import com.majing.shortlink.project.dao.mapper.LinkMapper;
import com.majing.shortlink.project.dto.req.LinkCreateReqDto;
import com.majing.shortlink.project.dto.req.LinkUpdateReqDto;
import com.majing.shortlink.project.dto.req.LinkedPageReqDto;
import com.majing.shortlink.project.dto.resp.LinkCountRespDto;
import com.majing.shortlink.project.dto.resp.LinkCreateRespDto;
import com.majing.shortlink.project.dto.resp.LinkedPageRespDto;
import com.majing.shortlink.project.service.LinkService;
import com.majing.shortlink.project.util.HashUtil;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.majing.shortlink.project.commom.constant.RedisKeyConstant.*;

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
    private final LinkGotoMapper linkGotoMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedissonClient redissonClient;
    @Transactional(rollbackFor = Exception.class)
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
        LinkGotoDo linkGotoDo = LinkGotoDo.builder()
                .gid(linkCreateReqDto.getGid())
                .fullShortUrl(fullShortLink).build();
        try{
            baseMapper.insert(linkDO);
            linkGotoMapper.insert(linkGotoDo);
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

    @Override
    public IPage<LinkedPageRespDto> pageLink(LinkedPageReqDto linkedPageReqDto) {
        LambdaQueryWrapper<LinkDO> queryWrapper = Wrappers.lambdaQuery(LinkDO.class)
                .eq(LinkDO::getGid, linkedPageReqDto.getGid())
                .eq(LinkDO::getDelFlag,0)
                .eq(LinkDO::getEnableStatus,1)
                .orderByDesc(LinkDO::getCreateTime);
        IPage<LinkDO> linkDOIPage = baseMapper.selectPage(linkedPageReqDto, queryWrapper);
        return linkDOIPage.convert(each -> BeanUtil.toBean(each, LinkedPageRespDto.class));
    }

    @Override
    public List<LinkCountRespDto> listGroupLinkCount(List<String> gidList) {
        QueryWrapper<LinkDO> queryWrapper = Wrappers.query(new LinkDO())
                .select("gid as gid, count(*) as linkCount")
                .in("gid", gidList)
                .eq("enable_status",1)
                .eq("del_flag",0)
                .groupBy("gid");
        List<Map<String, Object>> linkCountList = baseMapper.selectMaps(queryWrapper);
        return BeanUtil.copyToList(linkCountList, LinkCountRespDto.class);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateLink(LinkUpdateReqDto linkUpdateReqDto) {
        LambdaQueryWrapper<LinkDO> queryWrapper = Wrappers.lambdaQuery(LinkDO.class)
                .eq(LinkDO::getGid, linkUpdateReqDto.getGid())
                .eq(LinkDO::getFullShortUrl, linkUpdateReqDto.getFullShortUrl())
                .eq(LinkDO::getDelFlag,0)
                .eq(LinkDO::getEnableStatus,1);
        LinkDO hasLink = baseMapper.selectOne(queryWrapper);
        if(hasLink == null){
            throw new ClientException("短链接查找失败");
        }
        LinkDO newLink = LinkDO.builder()
                .domain(hasLink.getDomain())
                .shortUri(hasLink.getShortUri())
                .clickNum(hasLink.getClickNum())
                .fullShortUrl(hasLink.getFullShortUrl())
                .originUrl(linkUpdateReqDto.getOriginUrl())
                .gid(linkUpdateReqDto.getGid())
                .enableStatus(1)
                .favicon(hasLink.getFavicon())
                .createdType(hasLink.getCreatedType())
                .validDateType(linkUpdateReqDto.getValidDateType())
                .validDate(linkUpdateReqDto.getValidDate())
                .describe(linkUpdateReqDto.getDescribe())
                .build();

        if(Objects.equals(newLink.getGid(), hasLink.getGid())){
            LambdaUpdateWrapper<LinkDO> lambdaUpdateWrapper = Wrappers.lambdaUpdate(LinkDO.class)
                    .eq(LinkDO::getFullShortUrl, linkUpdateReqDto.getFullShortUrl())
                    .eq(LinkDO::getGid, linkUpdateReqDto.getGid())
                    .eq(LinkDO::getDelFlag,0)
                    .eq(LinkDO::getEnableStatus,1)
                    .set(Objects.equals(linkUpdateReqDto.getValidDateType(), validDateTypeEnum.PERMANENT.getType()),LinkDO::getValidDate,null);
            baseMapper.update(newLink, lambdaUpdateWrapper);
        }else{
            LambdaQueryWrapper<LinkDO> queryWrapperUpdate = Wrappers.lambdaQuery(LinkDO.class)
                    .eq(LinkDO::getFullShortUrl, linkUpdateReqDto.getFullShortUrl())
                    .eq(LinkDO::getGid, linkUpdateReqDto.getGid())
                    .eq(LinkDO::getDelFlag,0)
                    .eq(LinkDO::getEnableStatus,1);
            baseMapper.delete(queryWrapperUpdate);
            baseMapper.insert(newLink);
        }
    }

    @Override
    public void restoreUrl(String shortUrl, ServletRequest request, ServletResponse response) {
        String serverName = request.getServerName();
        String fullShortUrl = "http://" + serverName + "/" + shortUrl;
        //第一步：查询缓存中是否存在对应的原始链接，如果存在直接跳转，如果不存在执行第二步
        String originalUrl = stringRedisTemplate.opsForValue().get(String.format(LINK_GOTO_KEY, fullShortUrl));
        if(StrUtil.isBlank(originalUrl)){
            //第二步：对于缓存中不存在的原始链接可能失效或者数据库里不存在，通过布隆过滤器可以过滤掉数据库中不存在的情况
            boolean contains = shortUriCreateCachePenetrationBloomFilter.contains(fullShortUrl);
            if(contains){
                //第三步：如果布隆过滤器中存在，则会有误判的情况，由于后续对于数据库中不存在的情况会在缓存中存储空值，因此需要判断
                //缓存中是否存在空值，如果不存在需要继续判断，过滤掉第一次查询，尚未在缓存中存储空值的情况
                String gotoIsNullLink = stringRedisTemplate.opsForValue().get(String.format(Link_ISNULL_KEY,fullShortUrl));
                if(StrUtil.isBlank(gotoIsNullLink)){
                    //对同一个短链跳长链的请求加锁
                    RLock lock = redissonClient.getLock(String.format(LINK_LOCK_KEY, fullShortUrl));
                    lock.lock();
                    try {
                        //双重校验，用来应对上一个锁执行过程中已经将数据库的值存入缓存的情况
                        originalUrl = stringRedisTemplate.opsForValue().get(String.format(LINK_GOTO_KEY, fullShortUrl));
                        if(StrUtil.isBlank(originalUrl)){
                            LambdaQueryWrapper<LinkGotoDo> linkGotoDoLambdaQueryWrapper = Wrappers.lambdaQuery(LinkGotoDo.class)
                                    .eq(LinkGotoDo::getFullShortUrl, fullShortUrl);
                            LinkGotoDo linkGotoDo = linkGotoMapper.selectOne(linkGotoDoLambdaQueryWrapper);
                            if(linkGotoDo==null){
                                //严格来说要做风控
                                return;
                            }
                            LambdaQueryWrapper<LinkDO> linkDOLambdaQueryWrapper = Wrappers.lambdaQuery(LinkDO.class)
                                    .eq(LinkDO::getGid, linkGotoDo.getGid())
                                    .eq(LinkDO::getFullShortUrl, fullShortUrl)
                                    .eq(LinkDO::getEnableStatus,1)
                                    .eq(LinkDO::getDelFlag,0);
                            LinkDO linkDO = baseMapper.selectOne(linkDOLambdaQueryWrapper);
                            if(linkDO!=null){
                                //第一次从数据库获取之后，将数据存入缓存
                                stringRedisTemplate.opsForValue().set(String.format(LINK_GOTO_KEY, fullShortUrl), linkDO.getOriginUrl());
                                originalUrl = linkDO.getOriginUrl();
                            }else{
                                //数据库没有则存入空值
                                stringRedisTemplate.opsForValue().set(String.format(Link_ISNULL_KEY,fullShortUrl), "-",30, TimeUnit.MINUTES);
                            }
                        }
                    }finally {
                        lock.unlock();
                    }
                }
            }
        }
        if(originalUrl!=null){
            try {
                ((HttpServletResponse) response).sendRedirect(originalUrl);
            } catch (IOException e) {
                throw new ClientException("短链接重定向失败");
            }
        }else{
            throw new ClientException("短链接不存在");
        }
    }

    private String generateShortLink(LinkCreateReqDto linkCreateReqDto){
        int max = 0;
        String shortUri;
        while(true){
            if(max>10){
                throw new ServiceException("短链接频繁生产，请稍后再试");
            }
            String originUrl = linkCreateReqDto.getOriginUrl();
            //这里对于恶意请求，只能通过限流
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
