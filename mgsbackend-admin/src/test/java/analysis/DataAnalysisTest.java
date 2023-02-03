package analysis;

import com.mimiter.mgs.admin.App;
import com.mimiter.mgs.admin.service.ExhibitService;
import com.mimiter.mgs.admin.service.ExhibitionHallService;
import com.mimiter.mgs.admin.service.MGSUserService;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
public class DataAnalysisTest {

    @Resource
    MGSUserService userService;

    @Resource
    private ExhibitionHallService exhibitionHallService;

    @Test
    public void testNewUserCount() {
        Map<LocalDate, Integer> map = userService.getNewUserCount(1L, LocalDate.parse("2022-10-21"), LocalDate.parse("2022-11-30"));
        for (Map.Entry<LocalDate, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }

    @Test
    public void testTopKExhibitionHall() {
        Map<ExhibitionHall, Integer> map = exhibitionHallService.getTopKHotExhibitionHall(3L, 10);

        for (Map.Entry<ExhibitionHall, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }

    @Resource
    private ExhibitService exhibitService;

    @Test
    public void testTopKExhibit() {
        Map<Exhibit, Integer> map = exhibitService.getTopKHotExhibit(3L, 30);
        int sum = 0;
        for (Map.Entry<Exhibit, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
            sum += entry.getValue();
        }
        System.out.println(sum);
    }
}
