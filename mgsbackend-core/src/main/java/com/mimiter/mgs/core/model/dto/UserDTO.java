package com.mimiter.mgs.core.model.dto;

import com.mimiter.mgs.model.enums.AgeEnum;
import com.mimiter.mgs.model.enums.GenderEnum;
import lombok.Data;

/**
 * 前端展示用的用户信息
 */
@Data
public class UserDTO {

    private Long id;

    private String nickname;

    private String avatarUrl;

    private GenderEnum gender;

    private AgeEnum age;
}
