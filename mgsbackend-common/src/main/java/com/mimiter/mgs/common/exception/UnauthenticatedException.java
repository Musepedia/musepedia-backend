package com.mimiter.mgs.common.exception;

/**
 * 用户<b>未登录</b>但是访问了需要登录才能访问的资源时抛出。
 */
public class UnauthenticatedException extends RuntimeException {

    public UnauthenticatedException() {
        super();
    }

    public UnauthenticatedException(String message) {
        super(message);
    }
}
