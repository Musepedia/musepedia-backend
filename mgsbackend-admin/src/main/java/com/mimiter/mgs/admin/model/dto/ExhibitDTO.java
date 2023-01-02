package com.mimiter.mgs.admin.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("展品")
@Data
public class ExhibitDTO {

    private Long id;

    @ApiModelProperty("展品所在博物馆id")
    private Long museumId;

    @ApiModelProperty("展品所在展馆信息")
    private ExhibitionHall exhibitionHall;

    @ApiModelProperty("展品图片URL")
    private String figureUrl;

    @ApiModelProperty("展品标签，即展品名")
    private String label;

    @ApiModelProperty("展品描述")
    private String description;

    @ApiModelProperty("展品详情页链接")
    private String url;

    @ApiModelProperty(value = "上一个展品ID")
    private Long prevId;

    @ApiModelProperty(value = "下一个展品ID")
    private Long nextId;
}
