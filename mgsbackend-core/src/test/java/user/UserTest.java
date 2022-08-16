package user;

import cn.abstractmgs.core.App;
import cn.abstractmgs.core.model.entity.User;
import cn.abstractmgs.core.model.entity.UserWxOpenid;
import cn.abstractmgs.core.model.entity.enums.AgeEnum;
import cn.abstractmgs.core.model.entity.enums.GenderEnum;
import cn.abstractmgs.core.repository.UserRepository;
import cn.abstractmgs.core.repository.UserWxOpenidRepository;
import cn.abstractmgs.core.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UserTest {

    @Resource
    UserRepository userRepository;

    @Resource
    UserWxOpenidRepository openidRepository;

    @Resource
    private UserService userService;

    @Test
    public void test(){
        User user = new User();
        user.setNickname("nick");
        user.setPhoneNumber("158ss");
        user.setAvatarUrl("https");
        userRepository.insert(user);

        UserWxOpenid openId = new UserWxOpenid();
        openId.setWxOpenId("RANDOM");
        openId.setUserId(user.getId());
        openidRepository.insert(openId);
    }

    @Test
    public void select(){
        User u = userRepository.selectById(10000L);
        List<User> uu = userRepository.listByNickname("nick");
        User user = userRepository.getByOpenid("RANDOM");
    }

    @Test
    public void getterSetter() {
        System.out.println(userService.getUserLocation(10000L));;
        userService.setUserLocation(10000L, 1L);
        System.out.println(userService.getUserLocation(10000L));
    }

    @Test
    @Transactional
    public void test2() {
    }

    @Test
    public void testInsertQuestion() {
        // userService.insertUserQuestion(10003L, 353L);
    }

    @Test
    public void testUserRecommendStatus() {
//        userService.setUserRecommendStatus(10000L, true);
//        System.out.println(userService.getUserRecommendStatus(10000L));
        userService.setUserLocation(10000L, 1L);
        userService.setUserRecommendStatus(10000L, false);
        System.out.println(userService.getUserRecommendStatus(10000L));
        userService.setUserLocation(10000L, 1L);
        System.out.println(userService.getUserRecommendStatus(10000L));
        userService.setUserLocation(10000L, 2L);
        System.out.println(userService.getUserRecommendStatus(10000L));
    }
}
