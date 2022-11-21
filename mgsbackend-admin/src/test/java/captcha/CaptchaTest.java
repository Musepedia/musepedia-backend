package captcha;

import com.mimiter.mgs.admin.App;
import com.mimiter.mgs.admin.model.support.Captcha;
import com.mimiter.mgs.admin.service.CaptchaService;
import com.mimiter.mgs.common.exception.BadRequestException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
@Transactional
public class CaptchaTest {

    @Resource
    CaptchaService captchaService;

    @Test
    public void testGenerateCaptcha(){
        Captcha c = captchaService.generateCaptcha();
        Assert.assertNotNull(c);
        Assert.assertNotNull(c.getEntity());
        Assert.assertNotNull(c.getUuid());
        Assert.assertNotNull(c.getCode());
    }

    @Test
    public void testVerifyCorrectCaptcha(){
        Captcha c = captchaService.generateCaptcha();
        captchaService.verifyCaptcha(c.getUuid(), c.getCode());
    }

    @Test
    public void testVerifyWrongCaptcha(){
        Captcha c = captchaService.generateCaptcha();
        // wrong code
        Assert.assertThrows(BadRequestException.class, () -> captchaService.verifyCaptcha(c.getUuid(), "wrong code"));

        // uuid doesn't exists
        Assert.assertThrows(BadRequestException.class, () -> captchaService.verifyCaptcha("wrong uuid", "can be anything"));
    }

    @Test
    public void verifyCodeAgainIsNotAllowed(){
        Captcha c = captchaService.generateCaptcha();
        captchaService.verifyCaptcha(c.getUuid(), c.getCode());
        Assert.assertThrows(BadRequestException.class, () -> captchaService.verifyCaptcha(c.getUuid(), c.getCode()));

        // even if the code is wrong
        Captcha c2 = captchaService.generateCaptcha();
        Assert.assertThrows(BadRequestException.class, () -> captchaService.verifyCaptcha(c2.getUuid(), "wrong code"));
        Assert.assertThrows(BadRequestException.class, () -> captchaService.verifyCaptcha(c2.getUuid(), c2.getCode()));
    }

    @Test
    public void testInvalidateCaptcha(){
        Captcha c = captchaService.generateCaptcha();
        captchaService.invalidateCaptcha(c.getUuid());
        Assert.assertThrows(BadRequestException.class, () -> captchaService.verifyCaptcha(c.getUuid(), c.getCode()));
    }
}
