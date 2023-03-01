package exhibit;


import com.mimiter.mgs.admin.App;
import com.mimiter.mgs.admin.controller.ExhibitController;
import com.mimiter.mgs.model.entity.Exhibit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import util.TestUtil;

import javax.annotation.Resource;
import java.util.List;

import static com.mimiter.mgs.admin.service.RoleService.STR_SYS_ADMIN;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
@Transactional
public class OpenDocumentTest {

    @Resource
    ExhibitController exhibitController;

    @Test
    public void exhibitAliasTest(){
        TestUtil.loginAs(1L, STR_SYS_ADMIN);
        List<String> res = exhibitController.possibleAlias(126L).getData();
        System.out.println(res);
    }
}
