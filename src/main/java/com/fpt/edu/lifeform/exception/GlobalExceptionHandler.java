package com.fpt.edu.lifeform.exception;

import com.fpt.edu.lifeform.dto.response.ApiResponse;
import com.fpt.edu.lifeform.exception.custom.InvalidRequestInput;
import com.fpt.edu.lifeform.exception.custom.NotFoundException;
import com.fpt.edu.lifeform.utils.BuildResponse;
import org.hibernate.NonUniqueResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
            NotFoundException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            NonUniqueResultException.class,
            IllegalArgumentException.class,
            IOException.class,
            ArrayIndexOutOfBoundsException.class,
            InvalidRequestInput.class,
    })
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        ApiResponse<Void> apiResponse = BuildResponse.buildApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                "Exception!",
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(Objects.requireNonNull(e.getFieldError()).getDefaultMessage());
        apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        apiResponse.setErrorMessage("Exception!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }


}