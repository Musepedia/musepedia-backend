package com.mimiter.mgs.admin.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.model.entity.CulturalCreative;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.var;
import org.springframework.util.StringUtils;

@Data
@ApiModel("文创query")
public class CreativeQuery extends BaseQuery<CulturalCreative> {

    @ApiModelProperty(value = "展品对应博物馆ID", notes = "仅超级管理员可自由填写，其他默认只能查询自己博物馆的ID")
    private Long museumId;

    @ApiModelProperty(value = "文创名称", notes = "模糊查询")
    private String name;

    @Override
    public QueryWrapper<CulturalCreative> toQueryWrapper() {
        var wrapper = super.toQueryWrapper();
        if (museumId != null) {
            wrapper.eq("museum_id", museumId);
        }
        if (StringUtils.hasText(name)) {
            wrapper.like("name", name);
        }
        return wrapper;
    }
}
