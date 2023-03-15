package com.mimiter.mgs.core.model.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("问题反馈请求参数")
public class FeedbackParam {

    @NotNull(message = "问题ID不能为空")
    private Long questionId;

    @NotNull(message = "QA类型不能为空")
    private Integer qaType;

    @NotNull(message = "反馈不能为空")
    private Boolean feedback;
}
