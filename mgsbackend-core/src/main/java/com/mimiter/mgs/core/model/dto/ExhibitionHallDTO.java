package com.mimiter.mgs.core.model.dto;

import lombok.Data;

/**
 * 前端展示的展区信息
 */
@Data
public class ExhibitionHallDTO {

    private Long id;

    private String name;

    private String description;

    private String imageUrl;
}
