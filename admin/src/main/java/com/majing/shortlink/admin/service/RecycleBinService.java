package com.majing.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.majing.shortlink.admin.dao.entity.GroupDO;

import java.util.List;

/**
 * @author majing
 * @date 2024-05-02 22:00
 * @Description
 */
public interface RecycleBinService extends IService<GroupDO> {
    List<GroupDO> getGidList();
}
