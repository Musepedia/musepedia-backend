package cn.abstractmgs.core.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExhibitDTO extends SimpleExhibitDTO {

    private String description;

    private String url;

}
