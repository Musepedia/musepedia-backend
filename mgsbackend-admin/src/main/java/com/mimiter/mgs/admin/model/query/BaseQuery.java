package com.mimiter.mgs.admin.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 基础查询参数封装类，包含创建时间和更新时间的参数
 *
 * @param <T> 实体类
 */
@Data
@ApiModel("基础查询参数封装类")
public class BaseQuery<T> {

    public static final int DEFAULT_PAGE_SIZE = 10;

    @ApiModelProperty(value = "分页大小，默认10")
    private int size = DEFAULT_PAGE_SIZE;

    @ApiModelProperty(value = "当前页码，默认1")
    private int current = 1;

    private List<Long> createTime;

    private List<Long> updateTime;

    /**
     * 将查询参数封装成QueryWrapper
     *
     * @return QueryWrapper
     */
    public QueryWrapper<T> toQueryWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        if (createTime != null && createTime.size() > 0) {
            if (createTime.size() > 1) {
                queryWrapper.between("create_time", new Date(createTime.get(0)), new Date(createTime.get(1)));
            } else {
                queryWrapper.ge("create_time", new Date(createTime.get(0)));
            }
        }
        if (updateTime != null && updateTime.size() > 0) {
            if (updateTime.size() > 1) {
                queryWrapper.between("update_time", new Date(updateTime.get(0)), new Date(updateTime.get(1)));
            } else {
                queryWrapper.ge("update_time", new Date(updateTime.get(0)));
            }
        }
        return queryWrapper;
    }

    public Page<T> toPage() {
        return new Page<>(current, size);
    }
}
