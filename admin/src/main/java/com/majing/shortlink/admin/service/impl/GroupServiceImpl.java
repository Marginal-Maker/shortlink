package com.majing.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.majing.shortlink.admin.common.biz.user.UserContext;
import com.majing.shortlink.admin.common.convention.result.Result;
import com.majing.shortlink.admin.dao.entity.GroupDO;
import com.majing.shortlink.admin.dao.mapper.GroupMapper;
import com.majing.shortlink.admin.dto.req.GroupSortReqDto;
import com.majing.shortlink.admin.dto.req.GroupUpdateReqDto;
import com.majing.shortlink.admin.dto.resp.GroupRespDto;
import com.majing.shortlink.admin.remote.dto.LinkRemoteService;
import com.majing.shortlink.admin.remote.dto.resp.LinkCountRespDto;
import com.majing.shortlink.admin.service.GroupService;
import com.majing.shortlink.admin.util.RandomStringGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author majing
 * @date 2024-04-15 14:20
 * @Description 分组相关业务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService{

    LinkRemoteService linkRemoteService = new LinkRemoteService() {
    };

    @Override
    public void save(String groupName) {
        String gid ;
        do{
            gid= RandomStringGenerator.generateRandom();
        }while (hasGid(UserContext.getUsername(),gid));
        GroupDO groupDO = GroupDO.builder()
                .gid(gid)
                .name(groupName)
                .sortOrder(0)
                .username(UserContext.getUsername())
                .build();
        baseMapper.insert(groupDO);
    }

    public void save(String username, String groupName) {
        String gid ;
        do{
            gid= RandomStringGenerator.generateRandom();
        }while (hasGid(username, gid));
        GroupDO groupDO = GroupDO.builder()
                .gid(gid)
                .name(groupName)
                .sortOrder(0)
                .username(username)
                .build();
        baseMapper.insert(groupDO);
    }



    @Override
    public List<GroupRespDto> listGroup() {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .orderByDesc(GroupDO::getSortOrder, GroupDO::getUpdateTime);
        List<GroupDO> groupDOS = baseMapper.selectList(queryWrapper);
        Result<List<LinkCountRespDto>> listResult = linkRemoteService.listGroupLinkCount(groupDOS.stream().map(GroupDO::getGid).toList());
        List<GroupRespDto> groupRespDtoList = BeanUtil.copyToList(groupDOS, GroupRespDto.class);
        groupRespDtoList.forEach(each -> {
            Optional<LinkCountRespDto> first = listResult.getData().stream()
                    .filter(item-> item.getGid().equals(each.getGid()))
                    .findFirst();
            first.ifPresentOrElse(
                    item -> each.setLinkCount(first.get().getLinkCount()),
                    () -> {
                        each.setLinkCount(0); // 这里设置为默认值，你可以根据需求修改
                    }
            );
        });
        return groupRespDtoList;
    }

    @Override
    public void update(GroupUpdateReqDto groupUpdateReqDto) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, groupUpdateReqDto.getGid())
                .eq(GroupDO::getDelFlag,0);
        baseMapper.update(BeanUtil.toBean(groupUpdateReqDto, GroupDO.class), queryWrapper);
    }

    @Override
    public void delete(String gid) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getDelFlag,0);
        GroupDO groupDO = new GroupDO();
        groupDO.setDelFlag(1);
        baseMapper.update(groupDO, queryWrapper);
    }

    @Override
    public void sort(List<GroupSortReqDto> groupSortReqDtoList) {
        for(GroupSortReqDto groupSortReqDto : groupSortReqDtoList){
            LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                    .eq(GroupDO::getUsername, UserContext.getUsername())
                    .eq(GroupDO::getGid, groupSortReqDto.getGid())
                    .eq(GroupDO::getDelFlag, 0);
            GroupDO groupDO = GroupDO.builder()
                    .gid(groupSortReqDto.getGid())
                    .sortOrder(groupSortReqDto.getSortOrder())
                    .build();
            baseMapper.update(groupDO,queryWrapper);
        }

    }

    private Boolean hasGid(String username, String gid){
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getUsername, username);
        GroupDO groupDO = baseMapper.selectOne(queryWrapper);
        return groupDO!=null;
    }
}
