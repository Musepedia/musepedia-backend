package com.mimiter.mgs.admin.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mimiter.mgs.admin.model.enums.InstitutionType;
import com.mimiter.mgs.common.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel(value = "用户属于/管理哪个机构（博物馆、学校）")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId", callSuper = false)
@TableName(value = "sys_institution_admin", autoResultMap = true)
public class InstitutionAdmin extends BaseEntity {

    private Long userId;

    /**
     * 所属机构类型
     */
    private Long institutionId;

    /**
     * 所属机构类型
     */
    private InstitutionType type;
}
