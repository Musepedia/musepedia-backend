package cn.abstractmgs.core.model.entity.enums;

import cn.abstractmgs.common.model.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AgeEnum implements BaseEnum {
    PRESCHOOL(1, "学龄前"),
    PRIMARY_SCHOOL_LOWER(2, "小学低年级"),
    PRIMARY_SCHOOL_HIGHER(3, "小学高年级"),
    JUNIOR_HIGH_SCHOOL(4, "初中"),
    HIGH_SCHOOL(5, "高中"),
    ADULT(6, "高中");

    @EnumValue
    private final int age;

    private final String description;

    @Override
    public String getTextValue() {
        return age + "";
    }
}
