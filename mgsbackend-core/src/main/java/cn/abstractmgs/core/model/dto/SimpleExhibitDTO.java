package cn.abstractmgs.core.model.dto;

import lombok.Data;

@Data
public class SimpleExhibitDTO {

    private Long id;

    private String label;

    private String figureUrl;

    private Boolean hot;

    private ExhibitionHallDTO exhibitionHall;
}
