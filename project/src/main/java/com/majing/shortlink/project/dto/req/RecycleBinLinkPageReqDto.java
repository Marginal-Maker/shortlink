package com.majing.shortlink.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.majing.shortlink.project.dao.entity.LinkDO;
import lombok.Data;

import java.util.List;

/**
 * @author majing
 * @date 2024-05-02 21:46
 * @Description
 */
@Data
public class RecycleBinLinkPageReqDto extends Page<LinkDO> {
    List<String> gidList;
}
