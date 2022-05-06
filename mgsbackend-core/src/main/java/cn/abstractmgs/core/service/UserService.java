package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.entity.User;
import cn.abstractmgs.core.model.param.WxLoginParam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

public interface UserService extends IService<User> {

    User getByOpenId(String openid);

    User loginWx(WxLoginParam param);

    void setUserLocation(Long userId, Long exhibitionHallId);

    Long getUserLocation(Long userId);

    boolean isUserAtEndOfExhibitionHall(Long userId);
}
