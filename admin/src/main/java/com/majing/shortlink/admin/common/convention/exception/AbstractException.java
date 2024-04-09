package com.majing.shortlink.admin.common.convention.exception;

import com.majing.shortlink.admin.common.convention.errorcode.IErrorCode;
import lombok.Getter;
import org.springframework.util.StringUtils;
import java.util.Optional;

/**
 * @author majing
 * @date 2024-04-08 19:29
 * @Description 抽象异常
 */
@Getter
public class AbstractException extends RuntimeException{
    public final String errorCode;

    public final String errorMessage;

    public AbstractException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable);
        this.errorCode = errorCode.code();
        this.errorMessage = Optional.ofNullable(StringUtils.hasLength(message) ? message : null).orElse(errorCode.message());
    }
}
