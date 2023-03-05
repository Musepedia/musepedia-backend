package com.mimiter.mgs.core.model.dto;

import com.mimiter.mgs.model.enums.AgeEnum;
import com.mimiter.mgs.model.enums.GenderEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 前端展示用的用户信息
 */
@Data
public class UserDTO {

    private Long id;

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    private String avatarUrl;

    private GenderEnum gender;

    private AgeEnum age;
}
