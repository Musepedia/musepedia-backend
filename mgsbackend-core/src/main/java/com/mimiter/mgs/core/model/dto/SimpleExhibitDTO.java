package com.mimiter.mgs.core.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 展品DTO。
 */
@Data
public class SimpleExhibitDTO {

    private Long id;

    private String label;

    private String figureUrl;

    private List<String> figureUrlList;

    private Boolean hot;

    private ExhibitionHallDTO exhibitionHall;
}
