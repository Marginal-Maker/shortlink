package com.majing.shortlink.project.controller;

import com.majing.shortlink.project.commom.convention.result.Result;
import com.majing.shortlink.project.commom.convention.result.Results;
import com.majing.shortlink.project.service.UrlTitleService;
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
    private final UrlTitleService urlTitleService;
    @GetMapping("/api/short-link/title")
    public Result<String> getUrlTitle(@RequestParam("url") String url){
        return Results.success(urlTitleService.getUrlTitle(url));
    }
}
