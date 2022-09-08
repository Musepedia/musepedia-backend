package cn.abstractmgs.model.entity;

import cn.abstractmgs.common.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@TableName(value = "tbl_user_question", autoResultMap = true)
public class QuestionFeedback extends BaseEntity {

    @TableId(value = "user_question_id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long questionId;

    @NotNull(message = "反馈不能为空")
    private Boolean feedback;
}
