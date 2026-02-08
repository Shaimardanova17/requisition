package com.example.demo.exception;

public class BusinessException extends RuntimeException {

    private final ErrorMessages.ErrorCode errorCode;
    private final String field;
    private final String rejectedValue;

    public BusinessException(ErrorMessages.ErrorCode errorCode) {
        super(ErrorMessages.message(errorCode));
        this.errorCode = errorCode;
        this.field = null;
        this.rejectedValue = null;
    }

    public ErrorMessages.ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getField() {
        return field;
    }

    public String getRejectedValue() {
        return rejectedValue;
    }
}
