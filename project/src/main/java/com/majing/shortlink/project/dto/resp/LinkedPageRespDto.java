package com.majing.shortlink.project.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author majing
 * @date 2024-04-18 14:53
 * @Description 短链接分页功能返回参数
 */
@Data
public class LinkedPageRespDto {
    private Long id;
    private String domain; // 域名

    private String shortUri; // 短链接

    private String fullShortUrl; // 完整短链接

    private String originUrl; // 原始链接

    private String gid; // 分组标识

    private Integer validDateType; // 有效期类型 0：永久有效 1：用户自定义
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validDate; // 有效期

    // @Column(length = 1024) 指定了字段的长度
    private String describe; // 描述
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String favicon;
}
