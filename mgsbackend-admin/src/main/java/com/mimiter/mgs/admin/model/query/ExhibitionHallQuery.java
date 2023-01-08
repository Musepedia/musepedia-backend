package com.mimiter.mgs.admin.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.model.entity.Museum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
@ApiModel("查询展区信息参数封装类")
public class ExhibitionHallQuery extends BaseQuery<Museum> {

    @ApiModelProperty(value = "展区名称", notes = "模糊查询")
    private String name;

    @Override
    public QueryWrapper<Museum> toQueryWrapper() {
        QueryWrapper<Museum> wrapper = super.toQueryWrapper();
        if (StringUtils.hasText(name)) {
            wrapper.like("exhibition_hall_name", name);
        }
        return wrapper;
    }

}
