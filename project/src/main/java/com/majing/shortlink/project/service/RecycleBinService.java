package com.majing.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.majing.shortlink.project.dao.entity.LinkDO;
import com.majing.shortlink.project.dto.req.SaveRecycleBinReqDto;

/**
 * @author majing
 * @date 2024-05-02 18:33
 * @Description
 */
public interface RecycleBinService extends IService<LinkDO> {
    /**
     * 添加至回收站
     * @param saveRecycleBinReqDto
     * @return void
     * @created at 2024/5/2 18:35
    */
    void save(SaveRecycleBinReqDto saveRecycleBinReqDto);
}
