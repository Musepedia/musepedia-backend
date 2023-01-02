package com.mimiter.mgs.admin.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.model.entity.Museum;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 查询博物馆信息参数封装类
 */
@Data
public class MuseumQuery extends BaseQuery<Museum> {

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
