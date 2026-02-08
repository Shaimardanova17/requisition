package com.example.demo.exception;

public class ApiErrorResponse {

    private ErrorMessages.ErrorCode errorCode;
    private String message;
    private String field;
    private String rejectedValue;

    public ApiErrorResponse(ErrorMessages.ErrorCode errorCode, String message, String field, String rejectedValue) {
        this.errorCode = errorCode;
        this.message = message;
        this.field = field;
        this.rejectedValue = rejectedValue;
    }


    public ErrorMessages.ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorMessages.ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(String rejectedValue) {
        this.rejectedValue = rejectedValue;
    }
}
