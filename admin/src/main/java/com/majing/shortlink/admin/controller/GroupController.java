package com.majing.shortlink.admin.controller;

import com.majing.shortlink.admin.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author majing
 * @date 2024-04-15 14:36
 * @Description
 */
@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
}
