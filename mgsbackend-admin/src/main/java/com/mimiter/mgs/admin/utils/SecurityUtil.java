package com.mimiter.mgs.admin.utils;


/**
 * 保存当前线程的用户信息
 */
public class SecurityUtil {

    private static final ThreadLocal<Long> CURRENT_USER_ID = new ThreadLocal<>();

    // set current userid
    public static void setCurrentUserId(Long userId) {
        CURRENT_USER_ID.set(userId);
    }

    // get current userid
    public static Long getCurrentUserId() {
        return CURRENT_USER_ID.get();
    }

    public static void removeCurrentUserId() {
        CURRENT_USER_ID.remove();
    }

    public static boolean isLogin() {
        return CURRENT_USER_ID.get() != null;
    }
}
