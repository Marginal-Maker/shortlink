package com.majing.shortlink.admin.remote.dto.req;

import lombok.Data;

import java.util.Date;

/**
 * @author majing
 * @date 2024-04-17 15:04
 * @Description 创建短链接请求参数
 */
@Data
public class LinkCreateReqDto {
    private String domain; // 域名

    private String originUrl; // 原始链接


    private String gid; // 分组标识


    private Integer createdType; // 创建类型 0：控制台 1：接口

    private Integer validDateType; // 有效期类型 0：永久有效 1：用户自定义

    private Date validDate; // 有效期

    // @Column(length = 1024) 指定了字段的长度
    private String describe; // 描述

    private String favicon;
}
