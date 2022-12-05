package culturalcreative;

import com.mimiter.mgs.admin.App;
import com.mimiter.mgs.admin.service.CulturalCreativeSerivce;
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
    CulturalCreativeSerivce culturalCreativeService;

    @Test
    public void testAdd() {
        // Create a new CulturalCreative object
        CulturalCreative cc = new CulturalCreative();
        cc.setName("Test Creative");
        cc.setDescription("This is a test creative");
        cc.setImgs(Arrays.asList("img1.jpg", "img2.jpg"));
        cc.setMuseumId(1L);

        // Call the add method to save the object to the database
        culturalCreativeService.save(cc);

        // Query the database to verify that the object was saved correctly
        CulturalCreative result = culturalCreativeService.getById(cc.getId());
        assertEquals(cc.getName(), result.getName());
        assertEquals(cc.getDescription(), result.getDescription());
        assertEquals(cc.getImgs(), result.getImgs());
        assertEquals(cc.getMuseumId(), result.getMuseumId());
    }
}
