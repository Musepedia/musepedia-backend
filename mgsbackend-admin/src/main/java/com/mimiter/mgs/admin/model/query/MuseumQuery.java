package com.mimiter.mgs.admin.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.model.entity.Museum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
@ApiModel("查询博物馆信息参数封装类")
public class MuseumQuery extends BaseQuery<Museum> {

    @ApiModelProperty(value = "博物馆名称", notes = "模糊查询")
    private String name;

    @Override
    public QueryWrapper<Museum> toQueryWrapper() {
        QueryWrapper<Museum> wrapper = super.toQueryWrapper();
        if (StringUtils.hasText(name)) {
            wrapper.like("museum_name", name);
        }
        return wrapper;
    }

}
