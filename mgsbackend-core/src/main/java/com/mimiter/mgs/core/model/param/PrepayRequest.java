package com.mimiter.mgs.core.model.param;

import lombok.Data;

/**
 * 测试用支付请求
 */
@Data
public class PrepayRequest {

    private String code;

    private Long eventId;
}
