package com.majing.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.majing.shortlink.admin.dao.entity.GroupDO;

/**
 * @author majing
 * @date 2024-04-15 14:32
 * @Description 实现分组相关业务
 */
public interface GroupService extends IService<GroupDO> {
    void save(String groupName);
}
