package com.majing.shortlink.admin.common.convention.exception;

import com.majing.shortlink.admin.common.convention.errorcode.BaseErrorCode;
import com.majing.shortlink.admin.common.convention.errorcode.IErrorCode;

/**
 * @author majing
 * @date 2024-04-08 19:36
 * @Description 客户端异常
 */
public class ClientException extends AbstractException {
    public ClientException(IErrorCode errorCode) {
        this(null, null, errorCode);
    }

    public ClientException(String message) {
        this(message, null, BaseErrorCode.CLIENT_ERROR);
    }

    public ClientException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public ClientException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "ClientException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}

