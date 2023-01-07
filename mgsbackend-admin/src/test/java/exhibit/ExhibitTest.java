package exhibit;

import com.mimiter.mgs.admin.App;
import com.mimiter.mgs.admin.model.request.UpdateExhibitAliasReq;
import com.mimiter.mgs.admin.model.request.UpdateExhibitTextReq;
import com.mimiter.mgs.admin.model.request.UpsertExhibitReq;
import com.mimiter.mgs.admin.model.request.UpsertExhibitionHallReq;
import com.mimiter.mgs.admin.service.ExhibitAliasService;
import com.mimiter.mgs.admin.service.ExhibitService;
import com.mimiter.mgs.admin.service.ExhibitTextService;
import com.mimiter.mgs.admin.service.ExhibitionHallService;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import javax.annotation.Resource;
import java.util.ArrayList;


import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
@Transactional
public class ExhibitTest {

    @Resource
    private ExhibitionHallService exhibitionHallService;

    @Resource
    private ExhibitService exhibitService;

    @Resource
    private ExhibitTextService exhibitTextService;

    @Resource
    private ExhibitAliasService exhibitAliasService;

    @Resource
    private WebApplicationContext webApplicationContext;

    @Before
    public void setupMockMvc() {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void addExhibitTest() {
        UpsertExhibitReq req = new UpsertExhibitReq();
        req.setFigureUrl("testFUrl");
        req.setDescription("testDes");
        req.setHallId(1L);
        req.setLabel("testLab");
        req.setUrl("testUrl");
        req.setNextId(1L);
        req.setPrevId(-1L);

        Exhibit e = exhibitService.addExhibit(req);
        assertNotNull(e.getId());
        assertEquals(e.getDescription(),req.getDescription());
    }

    @Test
    public void updateExhibit() {
        UpsertExhibitReq req = new UpsertExhibitReq();
        req.setId(1L);
        req.setFigureUrl("testFUrl");
        req.setDescription("testDes");
        req.setHallId(1L);
        req.setLabel("testLab");
        req.setUrl("testUrl");
        req.setNextId(1L);
        req.setPrevId(-1L);

        boolean result = exhibitService.updateExhibit(req);
        assertTrue(result);
    }

    @Test
    public void deleteExhibit() {
        UpsertExhibitReq req = new UpsertExhibitReq();
        req.setFigureUrl("testFUrl");
        req.setDescription("testDes");
        req.setHallId(1L);
        req.setLabel("testLab");
        req.setUrl("testUrl");
        req.setNextId(1L);
        req.setPrevId(-1L);

        Exhibit e = exhibitService.addExhibit(req);
        boolean result = exhibitService.removeById(e.getId());
        assertTrue(result);
    }

    @Test
    public void addExhibitionHall() {
        UpsertExhibitionHallReq req = new UpsertExhibitionHallReq();
        req.setDescription("testDrs");
        req.setImageUrl("testUrl");
        req.setMuseumId(3L);
        req.setName("testName");

        ExhibitionHall e = exhibitionHallService.addExhibitionHall(req);
        assertNotNull(e.getId());
        assertEquals(e.getName(),req.getName());
    }

    @Test
    public void updateExhibitionHall() {
        UpsertExhibitionHallReq req = new UpsertExhibitionHallReq();
        req.setDescription("testDrs");
        req.setImageUrl("testUrl");
        req.setMuseumId(3L);
        req.setName("testName");

        ExhibitionHall e = exhibitionHallService.addExhibitionHall(req);

        UpsertExhibitionHallReq uReq = new UpsertExhibitionHallReq();
        uReq.setId(e.getId());
        uReq.setName("updateName");
        boolean result = exhibitionHallService.updateExhibitionHall(req);
        assertTrue(result);
    }

    @Test
    public void deleteExhibitionHall() {
        UpsertExhibitionHallReq req = new UpsertExhibitionHallReq();
        req.setDescription("testDrs");
        req.setImageUrl("testUrl");
        req.setMuseumId(3L);
        req.setName("testName");

        ExhibitionHall e = exhibitionHallService.addExhibitionHall(req);

        boolean result = exhibitionHallService.removeById(e.getId());
        assertTrue(result);
    }

    @Test
    public void updateExhibitionText() {
        ArrayList<String> text = new ArrayList<>();
        text.add("test1");
        text.add("test2");
        UpdateExhibitTextReq req = new UpdateExhibitTextReq();
        req.setExhibitId(155L);
        req.setExhibitText(text);

        boolean result = exhibitTextService.updateExhibitText(req);
        assertTrue(result);
    }

    @Test
    public void updateExhibitionAlias() {
        ArrayList<String> alias = new ArrayList<>();
        alias.add("test1");
        alias.add("test2");
        UpdateExhibitAliasReq req = new UpdateExhibitAliasReq();
        req.setExhibitId(155L);
        req.setExhibitAlias(alias);

        boolean result = exhibitAliasService.updateExhibitAlias(req);
        assertTrue(result);
    }
}
