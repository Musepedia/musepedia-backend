package culturalcreative;

import com.mimiter.mgs.admin.App;
import com.mimiter.mgs.admin.service.CulturalCreativeService;
import com.mimiter.mgs.model.entity.CulturalCreative;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
@Transactional
public class CulturalCreativeTest {

    @Resource
    CulturalCreativeService culturalCreativeService;

    @Test
    public void testAdd() {
        // Create a new CulturalCreative object
        CulturalCreative cc = new CulturalCreative();
        cc.setName("Creative3");
        cc.setDescription("今天的照片是否让你感觉到一丝寒冷？其实这不是雪，而是地球上最大的石膏沙漠。这是位于新墨西哥州的白沙国家公园，在过去的1.2万年里，周围的山脉产生了约45亿吨雪白的石膏沙。这里最初于1933年1月18日被指定为白沙国家纪念地，然后在2019年正式成为白沙国家公园。如今，这座国家公园里生活着成千上万的物种，其中许多物种已经逐渐进化出了白色，以便更好融入环境。");
        cc.setImageList(Arrays.asList("https://image-1314089900.cos.ap-shanghai.myqcloud.com/1674040727991AB1.jpg"));
        cc.setMuseumId(5L);

        // Call the add method to save the object to the database
        culturalCreativeService.save(cc);

        // Query the database to verify that the object was saved correctly
        CulturalCreative result = culturalCreativeService.getById(cc.getId());
        assertEquals(cc.getName(), result.getName());
        assertEquals(cc.getDescription(), result.getDescription());
        assertEquals(cc.getImageList(), result.getImageList());
        assertEquals(cc.getMuseumId(), result.getMuseumId());
    }
}
