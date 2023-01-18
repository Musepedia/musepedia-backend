package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@ApiModel("获取某时间段用户新增数量请求")
public class GetNewUserCountReq {

    @NotNull(message = "博物馆ID不能为空")
    @ApiModelProperty(value = "博物馆ID", notes = "超级管理员任意填写(非空)，其他管理员必须填写自己所属博物馆ID")
    private Long museumId;

    @NotNull(message = "开始日期不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始日期(包括)", notes = "格式为yyyy-MM-dd")
    private LocalDate beginDate;

    @NotNull(message = "结束日期不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束日期(包括)", notes = "格式为yyyy-MM-dd")
    private LocalDate endDate;
}
