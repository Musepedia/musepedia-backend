package com.mimiter.mgs.admin.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.model.entity.Exhibit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
@ApiModel("查询展品信息参数封装类")
public class ExhibitQuery extends BaseQuery<Exhibit> {

    @ApiModelProperty(value = "展品对应博物馆ID", notes = "仅超级管理员可自由填写，其他默认只能查询自己博物馆的ID")
    private Long museumId;

    @ApiModelProperty(value = "展品对应展区ID")
    private Long exhibitionHallId;

    @ApiModelProperty(value = "展品名称", notes = "模糊查询")
    private String label;

    @Override
    public QueryWrapper<Exhibit> toQueryWrapper() {
        QueryWrapper<Exhibit> wrapper = super.toQueryWrapper();
        if (museumId != null) {
            wrapper.eq("museum_id", museumId);
        }
        if (exhibitionHallId != null) {
            wrapper.eq("exhibition_hall_id", exhibitionHallId);
        }
        if (StringUtils.hasText(label)) {
            wrapper.like("exhibit_label", label);
        }
        return wrapper;
    }
}
