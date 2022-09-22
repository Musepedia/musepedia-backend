package com.mimiter.mgs.core.utils;

import static com.mimiter.mgs.core.utils.ThreadContextHolder.currentUserId;

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
