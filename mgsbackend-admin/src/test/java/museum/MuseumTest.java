package museum;

import com.mimiter.mgs.admin.App;
import com.mimiter.mgs.admin.config.security.CodeAuthenticationToken;
import com.mimiter.mgs.admin.model.request.AddMuseumReq;
import com.mimiter.mgs.admin.service.AdminMuseumService;
import com.mimiter.mgs.admin.service.CaptchaService;
import com.mimiter.mgs.admin.utils.SecurityUtil;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
@Transactional
public class MuseumTest {
    @Resource
    AdminMuseumService museumService;

    @Resource
    CaptchaService captchaService;

    @Resource
    private WebApplicationContext webApplicationContext;

    AddMuseumReq museum;

    @Before
    public void setupMockMvc() {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Before
    public void insertMuseum() {
        museum = new AddMuseumReq();
        museum.setName("String");
        museum.setDescription("Description");
        museum.setLogoUrl("logoUrl");
        museum.setImageUrl("imageUrl");
        museum.setLatitude(0.0);
        museum.setLongitude(0.0);
        museum.setAddress("String");
        museumService.addMuseum(museum);

    }

    @Test
    public void testApi() throws Exception{
        String[] roles = new String[]{"sys_admin"};
        List<GrantedAuthority> authorities = Arrays.stream(roles)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        CodeAuthenticationToken successToken = new CodeAuthenticationToken(authorities);
        successToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(successToken);
        SecurityUtil.setCurrentUserId(1L);

        AddMuseumReq req = new AddMuseumReq();
        req.setName("String");
        req.setDescription("Description");
        req.setLogoUrl("logoUrl");
        req.setImageUrl("imageUrl");
        req.setLatitude(0.0);
        req.setLongitude(0.0);
        req.setAddress("String");

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(req)
                .when()
                .post("/api/admin/addMuseum")
                .then()
                .log().headers()
                .statusCode(200);
    }

}
