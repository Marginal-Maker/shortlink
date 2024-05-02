package com.majing.shortlink.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.majing.shortlink.admin.common.biz.user.UserContext;
import com.majing.shortlink.admin.common.convention.exception.ServiceException;
import com.majing.shortlink.admin.dao.entity.GroupDO;
import com.majing.shortlink.admin.dao.mapper.GroupMapper;
import com.majing.shortlink.admin.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author majing
 * @date 2024-05-02 22:01
 * @Description
 */
@Service
@RequiredArgsConstructor
public class RecycleBinServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements RecycleBinService {
    private final GroupMapper groupMapper;
    @Override
    public List<GroupDO> getGidList() {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getDelFlag,0);
        List<GroupDO> groupDOS = groupMapper.selectList(queryWrapper);
        if(CollUtil.isEmpty(groupDOS)){
            throw new ServiceException("用户无分组信息");
        }
        return groupDOS;
    }
}
