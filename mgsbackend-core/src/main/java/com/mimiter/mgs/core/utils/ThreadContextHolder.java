package com.mimiter.mgs.core.utils;

/**
 * 保存当前线程的用户信息，博物馆信息
 */
public class ThreadContextHolder {

    protected static final ThreadLocal<Long> CURRENT_USER_ID = new ThreadLocal<>();

    protected static final ThreadLocal<Long> CURRENT_MUSEUM_ID = new ThreadLocal<>();

    public static void setCurrentMuseumId(Long id) {
        CURRENT_MUSEUM_ID.set(id);
    }

    public static Long getCurrentMuseumId() {
        return CURRENT_MUSEUM_ID.get();
    }

    public static void removeAll() {
        CURRENT_USER_ID.remove();
        CURRENT_MUSEUM_ID.remove();
    }
}
