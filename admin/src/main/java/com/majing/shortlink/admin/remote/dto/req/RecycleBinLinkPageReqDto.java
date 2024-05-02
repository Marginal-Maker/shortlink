package com.majing.shortlink.admin.remote.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author majing
 * @date 2024-05-02 21:46
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecycleBinLinkPageReqDto extends Page{
    List<String> gidList;
}
