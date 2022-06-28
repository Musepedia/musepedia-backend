package cn.abstractmgs.core.model.dto;

import lombok.Data;

@Data
public class MuseumDTO {

    private Long id;

    private String name;

    private String description;

    private String logoUrl;

    private Boolean service;
}
