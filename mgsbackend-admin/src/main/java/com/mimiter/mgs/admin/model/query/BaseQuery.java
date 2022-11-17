package com.mimiter.mgs.admin.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 基础查询参数封装类，包含创建时间和更新时间的参数
 *
 * @param <T> 实体类
 */
@Data
public class BaseQuery<T> {

    private List<Date> createTime;

    private List<Date> updateTime;

    /**
     * 将查询参数封装成QueryWrapper
     *
     * @return QueryWrapper
     */
    public QueryWrapper<T> toQueryWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        if (createTime != null && createTime.size() > 0) {
            if (createTime.size() > 1) {
                queryWrapper.between("create_time", createTime.get(0), createTime.get(1));
            } else {
                queryWrapper.ge("create_time", createTime.get(0));
            }
        }
        if (updateTime != null && updateTime.size() > 0) {
            if (updateTime.size() > 1) {
                queryWrapper.between("update_time", updateTime.get(0), updateTime.get(1));
            } else {
                queryWrapper.ge("update_time", updateTime.get(0));
            }
        }
        return queryWrapper;
    }
}
