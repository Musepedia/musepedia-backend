package cn.abstractmgs.core.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String nickname;

    private String avatarUrl;
}
