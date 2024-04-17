package com.majing.shortlink.project.controller;

import com.majing.shortlink.project.commom.convention.result.Result;
import com.majing.shortlink.project.commom.convention.result.Results;
import com.majing.shortlink.project.dto.req.LinkCreateReqDto;
import com.majing.shortlink.project.dto.resp.LinkCreateRespDto;
import com.majing.shortlink.project.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author majing
 * @date 2024-04-17 14:45
 * @Description 短连接控制层
 */
@RestController
@RequestMapping("api/short-link/v1")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService linkService;
    @PostMapping("/create")
    public Result<LinkCreateRespDto> createShortLink(@RequestBody LinkCreateReqDto linkCreateReqDto){
        linkService.createShortLink(linkCreateReqDto);
        return Results.success(null);

    }

}
