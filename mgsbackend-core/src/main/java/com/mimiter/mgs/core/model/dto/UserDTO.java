package com.mimiter.mgs.core.model.dto;

import com.mimiter.mgs.core.model.entity.enums.AgeEnum;
import com.mimiter.mgs.core.model.entity.enums.GenderEnum;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String nickname;

    private String avatarUrl;

    private GenderEnum gender;

    private AgeEnum age;
}
