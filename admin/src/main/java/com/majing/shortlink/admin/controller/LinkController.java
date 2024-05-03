package com.majing.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.majing.shortlink.admin.common.convention.result.Result;
import com.majing.shortlink.admin.common.convention.result.Results;
import com.majing.shortlink.admin.dao.entity.GroupDO;
import com.majing.shortlink.admin.remote.dto.LinkRemoteService;
import com.majing.shortlink.admin.remote.dto.req.*;
import com.majing.shortlink.admin.remote.dto.resp.LinkCreateRespDto;
import com.majing.shortlink.admin.remote.dto.resp.LinkedPageRespDto;
import com.majing.shortlink.admin.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author majing
 * @date 2024-04-22 14:41
 * @Description 短链接后管控制层
 */
@RestController
@RequestMapping("api/short-link/admin/v1")
@RequiredArgsConstructor
public class LinkController {
    private final RecycleBinService recycleBinService;
    LinkRemoteService linkRemoteService = new LinkRemoteService() {
    };
    @GetMapping("/page")
    public Result<IPage<LinkedPageRespDto>> pageLink(LinkedPageReqDto linkedPageReqDto){
        return linkRemoteService.pageLink(linkedPageReqDto);
    }
    @PostMapping("/create")
    public Result<LinkCreateRespDto> createShortLink(@RequestBody LinkCreateReqDto linkCreateReqDto){
        return linkRemoteService.createShortLink(linkCreateReqDto);
    }
    @PostMapping ("/update")
    public Result<Void> updateLink(@RequestBody LinkUpdateReqDto linkUpdateReqDto){
        linkRemoteService.updateLink(linkUpdateReqDto);
        return Results.success();
    }
    @PostMapping("/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody SaveRecycleBinReqDto saveRecycleBinReqDto){
        linkRemoteService.saveRecycleBin(saveRecycleBinReqDto);
        return Results.success();
    }
    @GetMapping("/recycle-bin/page")
    public Result<IPage<LinkedPageRespDto>> pageLink(RecycleBinLinkPageReqDto recycleBinLinkPageReqDto){
                recycleBinLinkPageReqDto.setGidList(recycleBinService.getGidList().stream().map(GroupDO::getGid).toList());
        return linkRemoteService.RecycleBinPageLink(recycleBinLinkPageReqDto);
    }
    @PostMapping("/recycle-bin/recover")
    public Result<Void> recoverRecycleBin(@RequestBody RecoverRecycleBinReqDto recoverRecycleBinReqDto){
        linkRemoteService.recoverRecycleBin(recoverRecycleBinReqDto);
        return Results.success();
    }
}
