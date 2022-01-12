package cn.abstractmgs.core.model.dto;

import cn.abstractmgs.core.model.entity.ExhibitionHall;
import lombok.Data;

@Data
public class PreferenceDTO {

    private String label;

    private ExhibitionHall exhibitionHall;

    private String figureUrl;

    private Boolean isHot;
}
