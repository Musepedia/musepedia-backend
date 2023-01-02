package com.mimiter.mgs.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于数据分析的展区DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataAnalysisExhibitionHallDTO {

    private Long id;

    private String name;

    private Integer trendingScore;
}
