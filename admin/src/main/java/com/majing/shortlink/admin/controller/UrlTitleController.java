package com.majing.shortlink.admin.controller;

import com.majing.shortlink.admin.common.convention.result.Result;
import com.majing.shortlink.admin.remote.dto.LinkRemoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author majing
 * @date 2024-04-25 15:08
 * @Description 获取原网站标题
 */
@RestController
@RequiredArgsConstructor
public class UrlTitleController {
    LinkRemoteService linkRemoteService = new LinkRemoteService() {
    };
    @GetMapping("/api/short-link/admin/v1/title")
    public Result<String> getUrlTitle(@RequestParam("url") String url){
        return linkRemoteService.getUrlTitle(url);
    }
}
