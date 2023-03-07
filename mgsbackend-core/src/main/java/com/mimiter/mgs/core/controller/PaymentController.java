package com.mimiter.mgs.core.controller;

import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.core.config.WeixinMpConfig;
import com.mimiter.mgs.core.model.param.PrepayRequest;
import com.mimiter.mgs.core.model.response.PrepayResponse;
import com.mimiter.mgs.core.service.UserService;
import com.mimiter.mgs.payment.PaymentServiceGrpc;
import com.mimiter.mgs.payment.PrepayReq;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.var;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final UserService userService;

    @GrpcClient("paymentService")
    private PaymentServiceGrpc.PaymentServiceBlockingStub paymentService;

    private final WeixinMpConfig wxConfig;

    private static final long EXPIRE_IN_MILLIS = 15 * 60 * 1000;

    @ApiOperation("获取调起RequestPayment的参数")
    @PostMapping("/prepay")
    public BaseResponse<PrepayResponse> prepay(@Validated @RequestBody PrepayRequest req) {
        String openId = userService.getUserOpenId(req.getCode());

        // example payment
        PrepayReq rpcReq = PrepayReq.newBuilder()
                .setAmount(1)
                .setDescription("测试报名费用：1")
                .setAppId(wxConfig.getAppid())
                .setOpenId(openId)
                .setExpireInMillis(EXPIRE_IN_MILLIS).build();
        var rpcResp = paymentService.prepay(rpcReq);

        var resp = new PrepayResponse();
        resp.setNonceStr(rpcResp.getNonceStr());
        resp.setTimestamp(rpcResp.getTimestamp());
        resp.setPaySign(rpcResp.getPaySign());
        resp.setPackageVal(rpcResp.getPackageVal());

        return BaseResponse.ok(resp);
    }
}
