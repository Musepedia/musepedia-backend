package cn.abstractmgs.core.model.dto;

import cn.abstractmgs.core.model.entity.enums.AgeEnum;
import cn.abstractmgs.core.model.entity.enums.GenderEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String nickname;

    private String avatarUrl;

    private GenderEnum gender;

    private AgeEnum age;
}
