package com.mimiter.mgs.admin.model.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页查询返回结果，包含符合分页查询的数据和总记录
 *
 * @param <D> 分页的数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<D> {

    private List<D> data;

    private Long total;

    public PageDTO(Page<D> page) {
        data = page.getRecords();
        total = page.getTotal();
    }

}
