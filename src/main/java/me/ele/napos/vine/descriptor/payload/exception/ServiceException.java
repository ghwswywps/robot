package me.ele.napos.vine.descriptor.payload.exception;

public class ServiceException extends me.ele.contract.exception.ServiceException {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String code, String message) {
        super(code, message);
    }

    public ServiceException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public ServiceException(String code, Throwable cause) {
        super(code, cause);
    }
}
