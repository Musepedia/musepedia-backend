package recommend;

import cn.abstractmgs.core.App;
import cn.abstractmgs.core.model.entity.ExhibitionHall;
import cn.abstractmgs.core.recommend.RecommendExhibitionHallService;
import cn.abstractmgs.core.service.ExhibitionHallService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    public void recommendHallTest(){
        List<ExhibitionHall> pref = exhibitionHallService.list(new LambdaQueryWrapper<ExhibitionHall>().between(ExhibitionHall::getId, 3, 7));
        ExhibitionHall pos = exhibitionHallService.getById(4);
        System.out.println(recommendService.getRecommendExhibitionHall(1L, pref, pos));
    }

}
