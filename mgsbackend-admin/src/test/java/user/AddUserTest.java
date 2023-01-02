package user;

import com.mimiter.mgs.admin.App;
import com.mimiter.mgs.admin.config.security.CodeAuthenticationToken;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.entity.InstitutionAdmin;
import com.mimiter.mgs.admin.model.entity.Role;
import com.mimiter.mgs.admin.model.enums.InstitutionType;
import com.mimiter.mgs.admin.model.request.AddUserReq;
import com.mimiter.mgs.admin.repository.InstitutionAdminRepository;
import com.mimiter.mgs.admin.service.AdminUserService;
import com.mimiter.mgs.admin.service.RoleService;
import com.mimiter.mgs.admin.utils.SecurityUtil;
import com.mimiter.mgs.common.exception.BadRequestException;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import util.TestUtil;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
@Transactional
public class AddUserTest {

    @Resource
    private InstitutionAdminRepository institutionAdminRepository;

    @Resource
    private AdminUserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private WebApplicationContext webApplicationContext;

    private Role sysAdmin;
    private Role museumAdmin;
    private Role schoolAdmin;

    @Before
    public void setup() {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        RestAssuredMockMvc.mockMvc(mockMvc);

        List<Role> roles = roleService.list();
        sysAdmin = roles.stream().filter(role -> role.getName().equals(RoleService.STR_SYS_ADMIN))
                .collect(Collectors.toList()).get(0);
        museumAdmin = roles.stream().filter(role -> role.getName().equals(RoleService.STR_MUSEUM_ADMIN))
                .collect(Collectors.toList()).get(0);
        schoolAdmin = roles.stream().filter(role -> role.getName().equals(RoleService.STR_SCHOOL_ADMIN))
                .collect(Collectors.toList()).get(0);

        TestUtil.loginAs(1L, sysAdmin.getName());
    }

    @Test
    public void addUserSuccess() {
        AddUserReq req = new AddUserReq();
        req.setUsername("testadd");
        req.setPassword("testadd");
        req.setEmail("a@a.c");
        req.setPhone("13000000000");
        req.setNickname("testadd");
        req.setRoleIds(Arrays.asList(sysAdmin.getId()));

        AdminUser u = userService.addUser(req);
        assertNotNull(u.getId());
        assertEquals(u.getUsername(), req.getUsername());
        assertEquals(u.getEmail(), req.getEmail());
        assertEquals(u.getPhone(), req.getPhone());
        assertEquals(u.getNickname(), req.getNickname());

        List<Role> roles = roleService.listUserRoles(u.getId());
        assertEquals(roles.size(), 1);
        assertEquals(roles.get(0).getId(), sysAdmin.getId());
    }

    @Test
    public void usernameDuplicated() {
        AddUserReq req = new AddUserReq();
        req.setUsername("testadd");
        req.setPassword("testadd");
        req.setEmail("a@a.c");
        req.setPhone("13000000000");
        req.setNickname("testadd");
        req.setRoleIds(Arrays.asList(sysAdmin.getId()));
        AdminUser u = userService.addUser(req);
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(req));
    }

    @Test
    public void addMuseumOrSchoolAdmin(){
        AddUserReq req = new AddUserReq();
        req.setUsername("testadd");
        req.setPassword("testadd");
        req.setEmail("a@a.c");
        req.setPhone("13000000000");
        req.setNickname("testadd");
        req.setRoleIds(Arrays.asList(museumAdmin.getId()));
        req.setInstitutionId(1L);
        AdminUser u = userService.addUser(req);

        InstitutionAdmin ia = institutionAdminRepository.selectById(u.getId());
        assertNotNull(ia);
        assertEquals(ia.getInstitutionId(), req.getInstitutionId());
        assertEquals(ia.getType(), InstitutionType.MUSEUM);

        req.setRoleIds(Arrays.asList(schoolAdmin.getId()));
        req.setUsername("testadd2");
        u = userService.addUser(req);
        ia = institutionAdminRepository.selectById(u.getId());
        assertNotNull(ia);
        assertEquals(ia.getInstitutionId(), req.getInstitutionId());
        assertEquals(ia.getType(), InstitutionType.SCHOOL);
    }

    @Test
    public void addRoleNotExist() {
        AddUserReq req = new AddUserReq();
        req.setUsername("testadd");
        req.setPassword("testadd");
        req.setEmail("a@a.c");
        req.setPhone("13000000000");
        req.setNickname("testadd");
        req.setRoleIds(Arrays.asList(9000L));
        req.setInstitutionId(1L);
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(req));
    }

    @Test
    public void addMuseumAndSchoolAdmin(){
        AddUserReq req = new AddUserReq();
        req.setUsername("testadd");
        req.setPassword("testadd");
        req.setEmail("a@a.c");
        req.setPhone("13000000000");
        req.setNickname("testadd");
        req.setRoleIds(Arrays.asList(museumAdmin.getId(), schoolAdmin.getId()));
        req.setInstitutionId(1L);
        assertThrows(BadRequestException.class, () -> userService.addUser(req));

        req.setRoleIds(Arrays.asList(sysAdmin.getId(), schoolAdmin.getId(), museumAdmin.getId()));
        assertThrows(BadRequestException.class, () -> userService.addUser(req));
    }

    @Test
    public void addSysAndMuseumAdmin(){
        AddUserReq req = new AddUserReq();
        req.setUsername("testadd");
        req.setPassword("testadd");
        req.setEmail("a@a.c");
        req.setPhone("13000000000");
        req.setNickname("testadd");
        req.setRoleIds(Arrays.asList(sysAdmin.getId(), museumAdmin.getId()));
        req.setInstitutionId(1L);

        AdminUser u = userService.addUser(req);

        InstitutionAdmin ia = institutionAdminRepository.selectById(u.getId());
        assertNotNull(ia);
        assertEquals(ia.getInstitutionId(), req.getInstitutionId());
        assertEquals(ia.getType(), InstitutionType.MUSEUM);

        List<Role> roles = roleService.listUserRoles(u.getId());
        assertEquals(roles.size(), 2);
        assertTrue(roles.contains(sysAdmin));
        assertTrue(roles.contains(museumAdmin));
    }

}
