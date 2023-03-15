package com.mimiter.mgs.admin.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "问题DTO")
public class QuestionDTO {

    private Long id;

    @ApiModelProperty(value = "问题")
    private String questionText;

    @ApiModelProperty(value = "答案类型", notes = "0/null:无法回答, 1:文本答案, 2:地图(也是图片)答案, 3:图片答案")
    private int answerType;

    @ApiModelProperty(value = "回答文本")
    private String answerText;

    @ApiModelProperty(value = "问题对应展品ID")
    private Long exhibitId;

    @ApiModelProperty(value = "问题对应展品名称")
    private String exhibitName;

    @ApiModelProperty(value = "问题对应展品的博物馆ID")
    private Long museumId;

    @ApiModelProperty(value = "问题对应展品的博物馆名称")
    private String museumName;

    @ApiModelProperty(value = "问题被提问的次数")
    private Long questionFreq;

    @ApiModelProperty(value = "展品所在展区ID")
    private Long hallId;

    @ApiModelProperty(value = "展品所在展区名")
    private String hallName;
}
