package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.entity.User;
import cn.abstractmgs.core.model.entity.enums.AgeEnum;
import cn.abstractmgs.core.model.entity.enums.GenderEnum;
import cn.abstractmgs.core.model.param.PhoneLoginParam;
import cn.abstractmgs.core.model.param.WxLoginParam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService extends IService<User> {

    User getByOpenId(String openid);

    User loginWx(WxLoginParam param);

    User loginPhone(PhoneLoginParam param);

    void setUserLocation(Long userId, Long exhibitionHallId);

    Long getUserLocation(Long userId);

    boolean isUserAtEndOfExhibitionHall(Long userId);

    void setUserRecommendStatus(Long userId, boolean recommendStatus);

    boolean getUserRecommendStatus(Long userId);

    List<User> ageWithLabels(Long museumId);

}
