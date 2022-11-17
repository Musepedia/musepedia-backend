package museum;

import com.mimiter.mgs.core.App;
import com.mimiter.mgs.core.service.MuseumService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class MuseumTest {

    @Resource
    private MuseumService museumService;

    @Test
    public void getAllMuseum() {
        System.out.println(museumService.selectAllServicedMuseums());
    }
}
