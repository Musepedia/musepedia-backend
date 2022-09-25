package com.mimiter.mgs.common.exception.handler;

import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.common.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class, MissingServletRequestParameterException.class})
    public BaseResponse<?> validExceptionHandler(Exception exception) {
        return BaseResponse.error(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<?> validExceptionHandler(MethodArgumentNotValidException exception) {
        return BaseResponse.error(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    public BaseResponse<?> resourceNotFoundExceptionHandler(Exception exception){
        return new BaseResponse<>(404, exception.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BadRequestException.class,
            IllegalArgumentException.class,
            MultipartException.class})
    public BaseResponse<?> badRequestExceptionHandler(Exception exception){
        return BaseResponse.error(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UploadFailureException.class)
    public BaseResponse<?> uploadFailureExceptionHandler(UploadFailureException exception){
        return new BaseResponse<>(500,exception.getMessage(),null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalException.class)
    public BaseResponse<?> internalExceptionHandler(InternalException exception){
        log.error("InternalException: ", exception);
        return new BaseResponse<>(500, exception.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public BaseResponse<?> forbiddenExceptionHandler(ForbiddenException exception){
        return new BaseResponse<>(403, exception.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public BaseResponse<?> internalExceptionHandler(UnauthenticatedException exception){
        return new BaseResponse<>(401, exception.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseResponse<?> unhandledExceptionHandler(Exception exception){
        log.error("InternalException: ", exception);
        return new BaseResponse<>(500, exception.getMessage(), null);
    }

}
