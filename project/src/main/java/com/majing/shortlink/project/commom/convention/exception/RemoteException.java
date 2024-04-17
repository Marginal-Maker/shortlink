package com.majing.shortlink.project.commom.convention.exception;


import com.majing.shortlink.project.commom.convention.errorcode.BaseErrorCode;
import com.majing.shortlink.project.commom.convention.errorcode.IErrorCode;

/**
 * @author majing
 * @date 2024-04-08 19:39
 * @Description 远程调用异常
 */
public class RemoteException extends AbstractException {

    public RemoteException(String message) {
        this(message, null, BaseErrorCode.REMOTE_ERROR);
    }

    public RemoteException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public RemoteException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "RemoteException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
