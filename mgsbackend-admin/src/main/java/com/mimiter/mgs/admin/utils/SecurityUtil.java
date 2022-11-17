package com.mimiter.mgs.admin.utils;


import com.mimiter.mgs.admin.model.entity.AdminUser;

/**
 * 保存当前线程的用户信息
 */
public class SecurityUtil {

    private static final ThreadLocal<Long> CURRENT_USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<AdminUser> CURRENT_USER = new ThreadLocal<>();

    public static Long getCurrentUserId() {
        return CURRENT_USER_ID.get();
    }

    public static void setCurrentUserId(Long id) {
        CURRENT_USER_ID.set(id);
    }

    public static AdminUser getCurrentUser() {
        return CURRENT_USER.get();
    }

    public static void setCurrentUser(AdminUser id) {
        CURRENT_USER.set(id);
    }

    public static void removeCurrentUser() {
        CURRENT_USER_ID.remove();
        CURRENT_USER.remove();
    }

    public static boolean isLogin() {
        return CURRENT_USER_ID.get() != null;
    }
}
