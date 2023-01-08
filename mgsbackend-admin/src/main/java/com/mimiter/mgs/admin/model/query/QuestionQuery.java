package com.mimiter.mgs.admin.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.model.entity.RecommendQuestion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
@ApiModel(value = "查询推荐问题信息参数封装类")
public class QuestionQuery extends BaseQuery<RecommendQuestion> {

    private static final String[] SORT_FIELDS = {"question_freq"};

    @ApiModelProperty(value = "提问对应展品的博物馆ID", notes = "仅超级管理员可自由填写，其他默认只能查询自己博物馆的ID")
    private Long museumId;

    @ApiModelProperty(value = "答案类型", notes = "0:无法回答, 1:文本答案, 2:地图答案, 3:图片答案")
    private Integer answerType;

    @ApiModelProperty(value = "展品ID")
    private Long exhibitId;

    @ApiModelProperty(value = "问题", notes = "模糊查询")
    private String questionText;

    @ApiModelProperty(value = "回答文本", notes = "模糊查询")
    private String answerText;

    @Override
    public QueryWrapper<RecommendQuestion> toQueryWrapper() {
        QueryWrapper<RecommendQuestion> wrapper =  super.toQueryWrapper();
        if (museumId != null) {
            wrapper.eq("museum_id", museumId);
        }
        if (answerType != null) {
            if (answerType.equals(0)) {
                wrapper.isNull("answer_type");
            } else {
                wrapper.eq("answer_type", answerType);
            }
        }
        if (exhibitId != null) {
            wrapper.eq("exhibit_id", exhibitId);
        }
        if (StringUtils.hasText(questionText)) {
            wrapper.like("question_text", questionText);
        }
        if (StringUtils.hasText(answerText)) {
            wrapper.like("answer_text", answerText);
        }
        return wrapper;
    }

    @Override
    protected boolean allowOrder(String column) {
        for (String field : SORT_FIELDS) {
            if (field.equals(column)) {
                return true;
            }
        }
        return false;
    }
}
