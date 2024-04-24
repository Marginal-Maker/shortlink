package com.majing.shortlink.project.controller;

import com.majing.shortlink.project.service.LinkService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author majing
 * @date 2024-04-24 16:02
 * @Description
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class LinkGotoController {
    private final LinkService linkService;
    @GetMapping("/{short-uri}")
    public void restoreUrl(@PathVariable("short-uri") String shortUrl, ServletRequest request, ServletResponse response){
        linkService.restoreUrl(shortUrl, request, response);
    }
}
