package com.majing.shortlink.admin.controller;

import com.majing.shortlink.admin.common.convention.result.Result;
import com.majing.shortlink.admin.common.convention.result.Results;
import com.majing.shortlink.admin.dto.req.GroupDeleteDto;
import com.majing.shortlink.admin.dto.req.GroupSaveReqDto;
import com.majing.shortlink.admin.dto.req.GroupSortReqDto;
import com.majing.shortlink.admin.dto.req.GroupUpdateReqDto;
import com.majing.shortlink.admin.dto.resp.GroupRespDto;
import com.majing.shortlink.admin.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author majing
 * @date 2024-04-15 14:36
 * @Description
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/short-link/admin/v1/group")
public class GroupController {
    private final GroupService groupService;
    @PostMapping("/save")
    public Result<Void> save(@RequestBody GroupSaveReqDto groupSaveReqDto){
        groupService.save(groupSaveReqDto.getName());
        return Results.success();
    }
    @GetMapping("")
    public Result<List<GroupRespDto>> listGroup(){
        return Results.success(groupService.listGroup());
    }
    @PutMapping("")
    public Result<Void> update(@RequestBody GroupUpdateReqDto groupUpdateReqDto){
        groupService.update(groupUpdateReqDto);
        return Results.success();
    }
    @DeleteMapping("")
    public Result<Void> delete(@RequestBody GroupDeleteDto groupDeleteDto){
        groupService.delete(groupDeleteDto.getGid());
        return Results.success();
    }
    @PostMapping("/sort")
    public  Result<Void> sort(@RequestBody List<GroupSortReqDto> groupSortReqDtoList){
        groupService.sort(groupSortReqDtoList);
        return Results.success();
    }

}
