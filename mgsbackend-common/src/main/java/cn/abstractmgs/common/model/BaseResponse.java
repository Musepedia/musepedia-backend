package cn.abstractmgs.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    private Integer status;

    private String message;

    private T data;

    public static <T> BaseResponse<T> ok(){
        return new BaseResponse<>(200,"ok",null);
    }

    public static <T> BaseResponse<T> ok(String message,T data){
        return new BaseResponse<>(200,message,data);
    }

    /**
     * 如果返回数据的泛型为{@link String}，请使用{@link #ok(String, Object)} <br/>
     * <code>
     * BaseResponse&lt;String&gt; resp = BaseResponse.ok("ok", str);
     * </code>
     *
     * @param data /
     * @param <T> /
     * @return /
     */
    public static <T> BaseResponse<T> ok(T data){
        return BaseResponse.ok("ok",data);
    }

    /**
     * @see #ok(Object)
     * @param message /
     * @param <T> /
     * @return /
     */
    public static <T> BaseResponse<T> ok(String message){
        return BaseResponse.ok(message,null);
    }

    public static <T> BaseResponse<T> error(){
        return new BaseResponse<>(400,"",null);
    }

    public static <T> BaseResponse<T> error(String message, T data){
        return new BaseResponse<>(400,message,data);
    }

    public static <T> BaseResponse<T> error(String message){
        return BaseResponse.error(message,null);
    }
}
