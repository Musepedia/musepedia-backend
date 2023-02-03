package com.mimiter.mgs.admin.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用于数据分析的展品DTO")
public class DataAnalysisExhibitDTO {

    @ApiModelProperty("展品id")
    private Long id;

    @ApiModelProperty("展品名称")
    private String label;

    @ApiModelProperty("展品热门指数")
    private Integer trendingScore;
}
