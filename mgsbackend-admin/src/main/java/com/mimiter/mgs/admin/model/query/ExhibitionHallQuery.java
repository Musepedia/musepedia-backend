package com.mimiter.mgs.admin.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import com.mimiter.mgs.model.entity.Museum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
@ApiModel("查询展区信息参数封装类")
public class ExhibitionHallQuery extends BaseQuery<ExhibitionHall> {

    @ApiModelProperty(value = "展区对应博物馆ID", notes = "仅超级管理员可自由填写，其他默认只能查询自己博物馆的ID")
    private Long museumId;

    @ApiModelProperty(value = "展区名称", notes = "模糊查询")
    private String name;

    @Override
    public QueryWrapper<ExhibitionHall> toQueryWrapper() {
        QueryWrapper<ExhibitionHall> wrapper = super.toQueryWrapper();
        if (museumId != null) {
            wrapper.eq("museum_id", museumId);
        }
        if (StringUtils.hasText(name)) {
            wrapper.like("exhibition_hall_name", name);
        }
        return wrapper;
    }

}
