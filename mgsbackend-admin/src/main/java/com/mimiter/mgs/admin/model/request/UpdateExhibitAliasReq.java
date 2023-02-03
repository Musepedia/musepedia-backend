package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "更新展品别名请求参数")
@Data
public class UpdateExhibitAliasReq {

    @NotNull(message = "展品ID不能为空")
    private Long exhibitId;

    @ApiModelProperty("展品别名列表")
    private List<String> exhibitAlias;
}
