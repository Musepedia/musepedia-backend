package exhibit;

import com.mimiter.mgs.core.App;
import com.mimiter.mgs.core.service.ExhibitionHallService;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
public class ExhibitionHallTest {

    @Resource
    private ExhibitionHallService exhibitionHallService;

    @Test
    public void testTopKExhibitionHall() {
        Map<ExhibitionHall, Integer> map = exhibitionHallService.getTopKHotExhibitionHall(3L, 10);

        for (Map.Entry<ExhibitionHall, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }
}
