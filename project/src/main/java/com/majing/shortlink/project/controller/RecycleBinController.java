package com.majing.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.majing.shortlink.project.commom.convention.result.Result;
import com.majing.shortlink.project.commom.convention.result.Results;
import com.majing.shortlink.project.dto.req.RecoverRecycleBinReqDto;
import com.majing.shortlink.project.dto.req.RecycleBinLinkPageReqDto;
import com.majing.shortlink.project.dto.req.SaveRecycleBinReqDto;
import com.majing.shortlink.project.dto.resp.LinkedPageRespDto;
import com.majing.shortlink.project.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author majing
 * @date 2024-05-02 18:23
 * @Description 回收站控制层
 */

@RestController
@RequestMapping("api/short-link/v1")
@RequiredArgsConstructor
public class RecycleBinController {
    private final RecycleBinService recycleBinService;
    @PostMapping("/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody SaveRecycleBinReqDto saveRecycleBinReqDto){
        recycleBinService.save(saveRecycleBinReqDto);
        return Results.success();
    }

    @GetMapping("/recycle-bin/page")
    public Result<IPage<LinkedPageRespDto>> pageLink(RecycleBinLinkPageReqDto recycleBinLinkPageReqDto){
        return Results.success(recycleBinService.pageLink(recycleBinLinkPageReqDto));
    }
    @PostMapping("/recycle-bin/recover")
    public Result<Void> recoverRecycleBin(@RequestBody RecoverRecycleBinReqDto recoverRecycleBinReqDto){
        recycleBinService.recover(recoverRecycleBinReqDto);
        return Results.success();
    }
}
