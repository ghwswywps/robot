package me.ele.contract.exception;

/**
 * Application implementor should throw this when system-issue like jdbc-timeout or external-api-call-failure occurs. </br>Supports to be extended.
 */
@SuppressWarnings("serial")
public class ServerException extends Exception {
    private String code;

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String code, String message) {
        this(message);
        setCode(code);
    }

    public ServerException(String code, String message, Throwable cause) {
        super(message, cause);
        setCode(code);
    }

    public ServerException(String code, Throwable cause) {
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
