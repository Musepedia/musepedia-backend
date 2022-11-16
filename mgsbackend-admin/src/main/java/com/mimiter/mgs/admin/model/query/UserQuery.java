package com.mimiter.mgs.admin.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 管理员查询用户信息参数封装类
 */
@Data
public class UserQuery extends BaseQuery<AdminUser> {

    private String userId;

    private String realName;

    private Long departmentId;


    @Override
    public QueryWrapper<AdminUser> toQueryWrapper() {
        QueryWrapper<AdminUser> wrapper = super.toQueryWrapper();
        if (StringUtils.hasText(userId)) {
            wrapper.eq("user_id", userId);
        }
        if (StringUtils.hasText(realName)) {
            wrapper.like("real_name", realName);
        }
        if (departmentId != null && departmentId > 0) {
            wrapper.eq("department_id", departmentId);
        }
        return wrapper;
    }
}
