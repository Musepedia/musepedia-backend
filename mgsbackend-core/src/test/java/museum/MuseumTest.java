package museum;

import cn.abstractmgs.core.App;
import cn.abstractmgs.core.service.MuseumService;
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
