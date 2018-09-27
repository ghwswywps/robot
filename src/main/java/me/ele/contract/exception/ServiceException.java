package me.ele.contract.exception;

/**
 * Application implementor should throw this when business-issue like user-not-exist or invalid-access-mode occurs. </br>Supports to be extended.
 */
@SuppressWarnings("serial")
public class ServiceException extends Exception {
    private String code;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String code, String message) {
        this(message);
        setCode(code);
    }

    public ServiceException(String code, String message, Throwable cause) {
        super(message, cause);
        setCode(code);
    }

    public ServiceException(String code, Throwable cause) {
        super(cause);
        setCode(code);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
