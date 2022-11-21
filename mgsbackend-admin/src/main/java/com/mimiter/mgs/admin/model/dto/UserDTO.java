package com.mimiter.mgs.admin.model.dto;

import com.mimiter.mgs.admin.model.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * 给前端返回的用户信息
 */
@Data
public class UserDTO {

    private Long id;

    private String nickname;

    private String email;

    private String phone;

    private List<Role> roles;
}
