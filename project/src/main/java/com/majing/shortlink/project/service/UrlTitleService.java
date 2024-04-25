package com.majing.shortlink.project.service;

/**
 * @author majing
 * @date 2024-04-25 15:12
 * @Description url标题接口层
 */
public interface UrlTitleService {
    /**
     * 根据Url获取标题
     * @param url
     * @return java.lang.String
     * @created at 2024/4/25 15:13
    */
    String getUrlTitle(String url);
}
