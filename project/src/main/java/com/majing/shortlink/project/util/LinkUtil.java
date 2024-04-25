package com.majing.shortlink.project.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.Optional;

import static com.majing.shortlink.project.commom.constant.LinkConstant.DEFAULT_CACHE_VALID_TIME;

/**
 * @author majing
 * @date 2024-04-25 13:48
 * @Description
 */
public class LinkUtil {
    public static long getLinkCacheValidDate(Date validDate){
        return Optional.ofNullable(validDate).map(each -> DateUtil.between(new Date(), each, DateUnit.MS)).orElse(DEFAULT_CACHE_VALID_TIME
        );
    }
}
