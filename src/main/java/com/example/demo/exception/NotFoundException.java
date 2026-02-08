package com.example.demo.exception;

public class NotFoundException extends RuntimeException {

    private final ErrorMessages.ErrorCode errorCode;
    private final String field;
    private final String rejectedValue;

    public NotFoundException(ErrorMessages.ErrorCode errorCode) {
        super(ErrorMessages.message(errorCode));
        this.errorCode = errorCode;
        this.field = null;
        this.rejectedValue = null;
    }

    public ErrorMessages.ErrorCode getErrorCode() {
        return errorCode;
    }
}
