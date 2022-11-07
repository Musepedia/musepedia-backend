package com.mimiter.mgs.core.utils;

import static com.mimiter.mgs.core.utils.ThreadContextHolder.CURRENT_USER_ID;

/**
 * 保存当前线程的用户信息
 */
public class SecurityUtil {

    public static Long getCurrentUserId() {
        return CURRENT_USER_ID.get();
    }

    public static void setCurrentUserId(Long id) {
        CURRENT_USER_ID.set(id);
    }

    public static void removeCurrentUser() {
        CURRENT_USER_ID.remove();
    }

    public static boolean isLogin() {
        return CURRENT_USER_ID.get() != null;
    }
}
