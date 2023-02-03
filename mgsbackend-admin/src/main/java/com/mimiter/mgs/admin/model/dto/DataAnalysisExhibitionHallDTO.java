package com.mimiter.mgs.admin.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用于数据分析的展区DTO")
public class DataAnalysisExhibitionHallDTO {

    @ApiModelProperty("展区id")
    private Long id;

    @ApiModelProperty("展区名称")
    private String name;

    @ApiModelProperty("展区热门指数")
    private Integer trendingScore;
}
