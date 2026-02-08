package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.OptimisticLockException;

import static com.example.demo.exception.ErrorMessages.ErrorCode.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusiness(BusinessException ex) {

        return ResponseEntity
                .badRequest()
                .body(new ApiErrorResponse(
                        ex.getErrorCode(),
                        ex.getMessage(),
                        ex.getField(),
                        ex.getRejectedValue()
                ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {

        return ResponseEntity
                .badRequest()
                .body(new ApiErrorResponse(
                        INVALID_ARGUMENT,
                        ex.getMessage(),
                        null,
                        null
                ));
    }

    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<ApiErrorResponse> handleOptimistic(OptimisticLockException ex) {

        ErrorMessages.ErrorCode code =
                ErrorMessages.ErrorCode.valueOf(ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409
                .body(new ApiErrorResponse(
                        code,
                        ErrorMessages.message(code),
                        null,
                        null
                ));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(NotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(
                        ex.getErrorCode(),
                        ex.getMessage(),
                        null,
                        null
                ));
    }

}


