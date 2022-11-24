package user;

import com.mimiter.mgs.admin.App;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.request.AddUserReq;
import com.mimiter.mgs.admin.model.request.LoginReq;
import com.mimiter.mgs.admin.model.support.Captcha;
import com.mimiter.mgs.admin.service.AdminUserService;
import com.mimiter.mgs.admin.service.CaptchaService;
import com.mimiter.mgs.common.exception.BadRequestException;
import com.mimiter.mgs.common.utils.EnvironmentUtil;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
@Transactional
public class UserLoginTest {

    @Resource
    AdminUserService userService;

    @Resource
    CaptchaService captchaService;

    @Resource
    private WebApplicationContext webApplicationContext;

    AddUserReq userToLogin;

    @Before
    public void setupMockMvc() {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Before
    public void insertUserToLogin() {
        userToLogin = new AddUserReq();
        userToLogin.setUsername("__toLogin__");
        userToLogin.setPassword("usr2lgn");
        userToLogin.setNickname("user to login");
        userService.addUser(userToLogin);
    }

    @Test
    public void testSuccessLogin() {
        Captcha captcha = captchaService.generateCaptcha();

        LoginReq req = new LoginReq();
        req.setUsername(userToLogin.getUsername());
        req.setPassword(userToLogin.getPassword());
        req.setCode(captcha.getCode());
        req.setUuid(captcha.getUuid());

        AdminUser user = userService.loginPassword(req);
        Assert.assertNotNull(user);
        Assert.assertEquals(userToLogin.getUsername(), user.getUsername());
        Assert.assertEquals(userToLogin.getNickname(), user.getNickname());
    }

    @Test
    public void testWrongPassword() {
        Captcha captcha = captchaService.generateCaptcha();

        LoginReq req = new LoginReq();
        req.setUsername(userToLogin.getUsername());
        req.setPassword("wrong password");
        req.setCode(captcha.getCode());
        req.setUuid(captcha.getUuid());

        Assert.assertThrows(IllegalArgumentException.class, () -> userService.loginPassword(req));
    }

    @Test
    public void testInvalidCaptcha() {
        Captcha captcha = captchaService.generateCaptcha();

        LoginReq req = new LoginReq();
        req.setUsername(userToLogin.getUsername());
        req.setPassword(userToLogin.getPassword());
        req.setCode("wrong code");
        req.setUuid(captcha.getUuid());

        Assert.assertThrows(BadRequestException.class, () -> userService.loginPassword(req));
    }

    @Test
    public void testNoCaptcha() {
        LoginReq req = new LoginReq();
        req.setUsername(userToLogin.getUsername());
        req.setPassword(userToLogin.getPassword());

        Assert.assertThrows(IllegalArgumentException.class, () -> userService.loginPassword(req));
    }

    @Test
    public void testLoginApi2() throws Exception {
        Captcha captcha = captchaService.generateCaptcha();

        LoginReq req = new LoginReq();
        req.setUsername(userToLogin.getUsername());
        req.setPassword(userToLogin.getPassword());
        req.setCode(captcha.getCode());
        req.setUuid(captcha.getUuid());
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(req)
                .when()
                .post("/api/user/login")
                .then()
                .log().headers()
                .statusCode(200);
    }

}
