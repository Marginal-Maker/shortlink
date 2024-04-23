package com.majing.shortlink.project.commom.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author majing
 * @date 2024-04-23 15:06
 * @Description 有效期类型
 */
@RequiredArgsConstructor
public enum validDateTypeEnum {
    //永久有效
    PERMANENT(0),
    //自定义有效期
    CUSTOM(1);
    @Getter
    private final Integer type;
}
