package com.mimiter.mgs.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 用于数据分析的某日用户新增DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataAnalysisUserCountDTO {

    private LocalDate date;

    private int count;
}
