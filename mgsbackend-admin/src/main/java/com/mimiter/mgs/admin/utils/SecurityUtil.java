package com.mimiter.mgs.admin.utils;


import com.mimiter.mgs.admin.model.entity.AdminUser;

/**
 * 保存当前线程的用户信息
 */
public class SecurityUtil {

    private static final ThreadLocal<AdminUser> CURRENT_USER = new ThreadLocal<>();

    public static AdminUser getCurrentUser() {
        return CURRENT_USER.get();
    }

    public static void setCurrentUser(AdminUser id) {
        CURRENT_USER.set(id);
    }

    public static void removeCurrentUser() {
        CURRENT_USER.remove();
    }

    public static boolean isLogin() {
        return CURRENT_USER.get() != null;
    }
}
