package cn.abstractmgs.core.utils;

import cn.abstractmgs.core.model.entity.User;

import static cn.abstractmgs.core.utils.ThreadContextHolder.currentUserId;

public class SecurityUtil {

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
