package recommend;

import com.mimiter.mgs.core.App;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import com.mimiter.mgs.core.recommend.RecommendExhibitionHallService;
import com.mimiter.mgs.core.service.ExhibitionHallService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = App.class)
public class RecommendHallTest {

    @Resource
    RecommendExhibitionHallService recommendService;

    @Resource
    ExhibitionHallService exhibitionHallService;

    @Test
    public void recommendHallTest() throws JsonProcessingException {
        List<ExhibitionHall> pref = exhibitionHallService.list(new LambdaQueryWrapper<ExhibitionHall>().between(ExhibitionHall::getId, 3, 7));
        ExhibitionHall pos = exhibitionHallService.getById(4);
        System.out.println(recommendService.getRecommendExhibitionHall(1L, pref, pos));
    }

}
