package com.mimiter.mgs.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于数据分析的展品DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataAnalysisExhibitDTO {

    private Long id;

    private String label;

    private Integer trendingScore;
}
