package exhibit;

import com.mimiter.mgs.core.App;
import com.mimiter.mgs.core.service.ExhibitService;
import com.mimiter.mgs.model.entity.Exhibit;
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
public class ExhibitTest {

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
