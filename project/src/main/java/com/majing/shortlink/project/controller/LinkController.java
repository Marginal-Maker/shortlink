package com.majing.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.majing.shortlink.project.commom.convention.result.Result;
import com.majing.shortlink.project.commom.convention.result.Results;
import com.majing.shortlink.project.dto.req.LinkCreateReqDto;
import com.majing.shortlink.project.dto.req.LinkUpdateReqDto;
import com.majing.shortlink.project.dto.req.LinkedPageReqDto;
import com.majing.shortlink.project.dto.resp.LinkCountRespDto;
import com.majing.shortlink.project.dto.resp.LinkCreateRespDto;
import com.majing.shortlink.project.dto.resp.LinkedPageRespDto;
import com.majing.shortlink.project.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return Results.success(linkService.createShortLink(linkCreateReqDto));

    }
    @GetMapping("/page")
    public Result<IPage<LinkedPageRespDto>> pageLink(LinkedPageReqDto linkedPageReqDto){
        return Results.success(linkService.pageLink(linkedPageReqDto));
    }
    @GetMapping("/count")
    public Result<List<LinkCountRespDto>> listGroupLinkCount(@RequestParam("gidList") List<String> gidList){
        return Results.success(linkService.listGroupLinkCount(gidList));
    }
    @PostMapping ("/update")
    public Result<Void> updateLink(@RequestBody LinkUpdateReqDto linkUpdateReqDto){
        linkService.updateLink(linkUpdateReqDto);
        return Results.success();
    }
}
