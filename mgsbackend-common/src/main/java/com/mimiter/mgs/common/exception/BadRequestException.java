package com.mimiter.mgs.common.exception;

/**
 * 用于表示非法请求的异常，在任何由于<b>用户</b>引起的非法操作可以抛出次异常。
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }
}
