package com.majing.shortlink.admin.controller;

import com.majing.shortlink.admin.common.convention.result.Result;
import com.majing.shortlink.admin.common.convention.result.Results;
import com.majing.shortlink.admin.dto.req.GroupSaveReqDto;
import com.majing.shortlink.admin.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author majing
 * @date 2024-04-15 14:36
 * @Description
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/short-link/v1/group")
public class GroupController {
    private final GroupService groupService;
    @PostMapping("/save")
    public Result<Void> save(@RequestBody GroupSaveReqDto groupSaveReqDto){
        groupService.save(groupSaveReqDto.getName());
        return Results.success();
    }

}