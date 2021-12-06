package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.entity.User;
import cn.abstractmgs.core.model.param.WxLoginParam;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {

    User getByOpenId(String openid);

    User loginWx(WxLoginParam param);
}
