package com.mimiter.mgs.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    private Date createTime;

    private Date updateTime;

}
