package cn.abstractmgs.core.utils;

import cn.abstractmgs.core.model.entity.User;

public class SecurityUtil {

    private final static ThreadLocal<Long> currentUserId = new ThreadLocal<>();

    public static Long getCurrentUserId(){
        return currentUserId.get();
    }

    public static void setCurrentUserId(Long id){
        currentUserId.set(id);
    }

    public static void removeCurrentUser(){
        currentUserId.remove();
    }

    public static boolean isLogin(){
        return currentUserId.get() != null;
    }
}
