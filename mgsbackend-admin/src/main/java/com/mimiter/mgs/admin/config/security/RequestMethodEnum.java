package com.mimiter.mgs.admin.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请求方法枚举，用于{@link SecurityConfigUtil}添加SpringSecurity的鉴权拦截路径
 */
@Getter
@AllArgsConstructor
public enum RequestMethodEnum {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    ALL("All");

    /**
     * Request 类型
     */
    private final String type;

    /**
     * <p>在枚举中的寻找给定字符串type对应的枚举</p>
     *
     * @param type 给定类型，只包括<code>GET, POST, PUT, PATCH, DELETE</code>
     * @return 对应枚举，没有对应枚举的type会返回ALL类型
     */
    public static RequestMethodEnum find(String type) {
        for (RequestMethodEnum value : RequestMethodEnum.values()) {
            if (type.equals(value.getType())) {
                return value;
            }
        }
        return ALL;
    }
}
