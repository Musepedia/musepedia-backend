package com.mimiter.mgs.admin.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
@ApiModel("查询用户信息参数封装类")
public class UserQuery extends BaseQuery<AdminUser> {

    @ApiModelProperty(value = "昵称/称呼", notes = "模糊查询")
    private String nickname;

    @Override
    public QueryWrapper<AdminUser> toQueryWrapper() {
        QueryWrapper<AdminUser> wrapper = super.toQueryWrapper();
        if (StringUtils.hasText(nickname)) {
            wrapper.like("nickname", nickname);
        }
        return wrapper;
    }
}
