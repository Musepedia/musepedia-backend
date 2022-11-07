package com.mimiter.mgs.core.model.dto;

import lombok.Data;

/**
 * 前端展示的博物馆信息
 */
@Data
public class MuseumDTO {

    private Long id;

    private String name;

    private String description;

    private String logoUrl;

    private String imageUrl;

    private String address;

    private Boolean service;

    private Double longitude;

    private Double latitude;
}
