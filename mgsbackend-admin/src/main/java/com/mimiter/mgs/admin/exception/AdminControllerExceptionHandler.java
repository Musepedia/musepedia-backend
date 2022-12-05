package com.mimiter.mgs.admin.exception;

import com.mimiter.mgs.common.model.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class AdminControllerExceptionHandler {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public BaseResponse<?> forbiddenExceptionHandler(AccessDeniedException exception) {
        return BaseResponse.error(HttpStatus.FORBIDDEN.value(), exception.getMessage());
    }
}
