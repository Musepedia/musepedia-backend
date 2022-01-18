package cn.abstractmgs.core.model.dto;

import lombok.Data;

@Data
public class ExhibitDTO {

    private String label;

    private String description;

    private String url;

    private String figureUrl;

    private Boolean hot;

    private ExhibitionHallDTO exhibitionHall;

}
