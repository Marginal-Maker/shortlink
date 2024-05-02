package com.majing.shortlink.project.controller;

import com.majing.shortlink.project.commom.convention.result.Result;
import com.majing.shortlink.project.commom.convention.result.Results;
import com.majing.shortlink.project.dto.req.SaveRecycleBinReqDto;
import com.majing.shortlink.project.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
