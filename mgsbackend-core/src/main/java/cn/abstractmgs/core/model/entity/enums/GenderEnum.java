package cn.abstractmgs.core.model.entity.enums;

import cn.abstractmgs.common.model.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderEnum implements BaseEnum {
    FEMALE(1, "女性"),
    MALE(2, "男性"),
    OTHER(3, "其他"),
    UNKNOWN(4, "未知");

    @EnumValue
    private final int gender;

    private final String description;

    @Override
    public String getTextValue() {
        return gender + "";
    }
}
