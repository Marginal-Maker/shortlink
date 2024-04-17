package com.majing.shortlink.project.commom.convention.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
/**
 * @author majing
 * @date 2024-04-08 18:51
 * @Description 全局响应对象格式规约
 */
@Data
//@Accessors(chain = true)是Lombok注解中的一个功能,
// 它的作用是为类的 setter 方法生成一种流畅的、链式调用的方式。
// 通常情况下，使用这个注解后，可以通过调用一个setter方法后返回对象本身，
// 从而实现对同一个对象的多个属性进行设置，形成一条链式调用。
@Accessors(chain = true)
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 5679018624309023727L;

    /**
     * 正确返回码
     */
    public static final String SUCCESS_CODE = "0";

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 请求ID，在复杂系统中和全链路ID绑定
     */
    private String requestId;

    public boolean isSuccess() {
        return SUCCESS_CODE.equals(code);
    }
}
