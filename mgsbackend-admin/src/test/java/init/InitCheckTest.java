package init;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.admin.App;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.entity.Role;
import com.mimiter.mgs.admin.repository.RoleRepository;
import com.mimiter.mgs.admin.service.AdminUserService;
import com.mimiter.mgs.admin.service.RoleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
@Transactional
public class InitCheckTest {

    @Resource
    AdminUserService adminUserService;

    @Resource
    RoleRepository roleRepository;

    @Test
    public void ensureRoleExists(){
        for (Role role : RoleService.DEFAULT_ROLES) {
            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", role.getName());
            Assert.assertEquals(1L, roleRepository.selectCount(queryWrapper).longValue());
        }
    }

    @Test
    public void ensureAdminExists(){
        AdminUser user = adminUserService.findByUsername("mgs_admin");
        Assert.assertNotNull(user);

        List<Role> roles = roleRepository.findByUserId(user.getId());
        Assert.assertEquals(1, roles.size());
        Assert.assertEquals(RoleService.SYS_ADMIN.getName(), roles.get(0).getName());
    }
}
