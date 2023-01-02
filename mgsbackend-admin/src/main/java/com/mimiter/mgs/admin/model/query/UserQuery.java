package com.mimiter.mgs.admin.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 查询用户信息参数封装类
 */
@Data
public class UserQuery extends BaseQuery<AdminUser> {

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
