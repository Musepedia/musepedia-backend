package com.mimiter.mgs.admin.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用于数据分析的某日用户新增DTO")
public class DataAnalysisUserCountDTO {

    @ApiModelProperty("日期")
    private LocalDate date;

    @ApiModelProperty("新增用户数量")
    private int count;
}
