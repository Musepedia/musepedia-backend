package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("更新问题请求参数封装类")
public class UpdateQuestionReq {

    @NotNull(message = "问题ID不能为空")
    @ApiModelProperty(value = "问题ID")
    private Long id;

    @NotBlank(message = "回答不能为空")
    @ApiModelProperty(value = "回答文本", notes = "回答为图片时，此处为图片URL")
    private String answerText;

    @NotNull(message = "答案类型不能为空")
    @ApiModelProperty(value = "答案类型", notes = "1:文本答案, 2:地图答案, 3:图片答案")
    private Integer answerType;

    @ApiModelProperty(value = "问题对应展品ID")
    private Long exhibitId;
}
