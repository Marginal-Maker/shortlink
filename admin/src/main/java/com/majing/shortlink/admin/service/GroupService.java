package com.majing.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.majing.shortlink.admin.dao.entity.GroupDO;
import com.majing.shortlink.admin.dto.req.GroupSortReqDto;
import com.majing.shortlink.admin.dto.req.GroupUpdateReqDto;
import com.majing.shortlink.admin.dto.resp.GroupRespDto;

import java.util.List;

/**
 * @author majing
 * @date 2024-04-15 14:32
 * @Description 实现分组相关业务
 */
public interface GroupService extends IService<GroupDO> {
    /**
     * 新增短连接分组
     * @param groupName
     * @return void
     * @created at 2024/4/15 16:04
    */
    void save(String groupName);
    void save(String username, String groupName);
    /**
     * 查询短连接分组
     * @param
     * @return java.util.List<com.majing.shortlink.admin.dto.resp.GroupRespDto>
     * @created at 2024/4/15 16:07
    */
    List<GroupRespDto> listGroup();
    /**
     * 更新分组信息
     * @param groupUpdateReqDto
     * @return void
     * @created at 2024/4/15 20:04
    */
    void update(GroupUpdateReqDto groupUpdateReqDto);
    /**
     * 删除
     * @param gid
     * @return void
     * @created at 2024/4/17 12:55
    */
    void delete(String gid);
    /**
     * 排序
     * @param groupSortReqDtoList
     * @return void
     * @created at 2024/4/17 12:56
    */
    void sort(List<GroupSortReqDto> groupSortReqDtoList);
}
