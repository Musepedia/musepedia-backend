package exhibit;

import com.mimiter.mgs.core.App;
import com.mimiter.mgs.core.service.ExhibitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
public class ExhibitTest {

    @Resource
    private ExhibitService exhibitService;

    @Test
    public void testSelectExhibit() {
        System.out.println(exhibitService.selectExhibitByLabelAndMuseumId("阴丹士林", 3L));
    }
}
