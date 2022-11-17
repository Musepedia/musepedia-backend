package com.mimiter.mgs.core.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@inheritDoc}
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExhibitDTO extends SimpleExhibitDTO {

    private String description;

    private String url;

}
