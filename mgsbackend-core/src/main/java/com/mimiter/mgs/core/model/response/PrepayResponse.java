package com.mimiter.mgs.core.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 微信支付RPC请求
 */
@Data
public class PrepayResponse {

    @JsonProperty("package")
    private String packageVal;

    @JsonProperty("timeStamp")
    private String timestamp;

    private String paySign;

    private String nonceStr;

    private String outTradeNo;
}
