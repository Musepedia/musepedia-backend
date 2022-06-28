package cn.abstractmgs.core.utils;

public class ThreadContextHolder {

    final static ThreadLocal<Long> currentUserId = new ThreadLocal<>();

    final static ThreadLocal<Long> currentMuseumId = new ThreadLocal<>();

    public static void setCurrentMuseumId(Long id){
        currentMuseumId.set(id);
    }

    public static Long getCurrentMuseumId(){
        return currentMuseumId.get();
    }

    public static void removeAll(){
        currentUserId.remove();
        currentMuseumId.remove();
    }
}
