package com.mimiter.mgs.admin.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.model.entity.GPTCompletion;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class GPTCompletionQuery extends BaseQuery<GPTCompletion> {

    @ApiModelProperty(value = "提问对应展品的博物馆ID", notes = "仅超级管理员可自由填写，其他默认只能查询自己博物馆的ID")
    private Long museumId;

    @ApiModelProperty(value = "问题", notes = "模糊查询")
    private String questionText;

    @ApiModelProperty(value = "回答文本", notes = "模糊查询")
    private String answerText;

    @Override
    public QueryWrapper<GPTCompletion> toQueryWrapper() {
        QueryWrapper<GPTCompletion> wrapper = super.toQueryWrapper();
        if (museumId != null) {
            wrapper.eq("museum_id", museumId);
        }
        if (StringUtils.hasText(questionText)) {
            wrapper.like("user_question", questionText);
        }
        if (StringUtils.hasText(answerText)) {
            wrapper.like("gpt_completion", answerText);
        }
        return wrapper;
    }
}
