package com.mimiter.mgs.admin.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.Museum;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 查询展品信息参数封装类
 */
@Data
public class ExhibitQuery extends BaseQuery<Exhibit> {

    private Long museumId;

    private Long exhibitionHallId;

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
