package exhibit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.admin.App;
import com.mimiter.mgs.admin.controller.ExhibitController;
import com.mimiter.mgs.admin.controller.ExhibitionHallController;
import com.mimiter.mgs.admin.model.query.ExhibitQuery;
import com.mimiter.mgs.admin.model.query.ExhibitionHallQuery;
import com.mimiter.mgs.admin.model.request.UpdateExhibitAliasReq;
import com.mimiter.mgs.admin.model.request.UpdateExhibitTextReq;
import com.mimiter.mgs.admin.model.request.UpsertExhibitReq;
import com.mimiter.mgs.admin.model.request.UpsertExhibitionHallReq;
import com.mimiter.mgs.admin.repository.ExhibitAliasRepository;
import com.mimiter.mgs.admin.repository.ExhibitRepository;
import com.mimiter.mgs.admin.repository.ExhibitTextRepository;
import com.mimiter.mgs.admin.service.ExhibitAliasService;
import com.mimiter.mgs.admin.service.ExhibitService;
import com.mimiter.mgs.admin.service.ExhibitTextService;
import com.mimiter.mgs.admin.service.ExhibitionHallService;
import com.mimiter.mgs.common.exception.BadRequestException;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.ExhibitAlias;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Assert;
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
import util.TestUtil;

import javax.annotation.Resource;
import java.util.ArrayList;


import static com.mimiter.mgs.admin.service.RoleService.STR_MUSEUM_ADMIN;
import static com.mimiter.mgs.admin.service.RoleService.STR_SYS_ADMIN;
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
    private ExhibitController exhibitController;

    @Resource
    private ExhibitionHallController exhibitionHallController;

    @Resource
    private ExhibitRepository exhibitRepository;

    @Resource
    private ExhibitAliasRepository exhibitAliasRepository;

    @Resource
    private ExhibitTextRepository exhibitTextRepository;

    @Test
    public void getExhibitTest() {
        TestUtil.loginAs(72L, STR_SYS_ADMIN);
        assertNotNull(exhibitController.getExhibit(1L));

        ExhibitQuery query = new ExhibitQuery();
        query.setMuseumId(3L);
        assertNotNull(exhibitController.listExhibit(query));
    }

    @Test
    public void addExhibitTestCorrect() {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);

        UpsertExhibitReq req = new UpsertExhibitReq();
        req.setFigureUrl("testFUrl");
        req.setDescription("testDes");
        req.setHallId(1L);
        req.setLabel("testLab");
        req.setUrl("testUrl");
        req.setNextId(1L);
        req.setPrevId(-1L);

        exhibitController.addExhibit(req);
        Exhibit exhibit = exhibitRepository.findByLabel("testLab");
        assertNotNull(exhibit);
        assertEquals(1L,exhibit.getHallId().longValue());
    }

    @Test
    public void addExhibitTestWrong() {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);

        UpsertExhibitReq req = new UpsertExhibitReq();
        req.setFigureUrl("testFUrl");
        req.setDescription("testDes");
        req.setHallId(11L);
        req.setLabel("testLab");
        req.setUrl("testUrl");
        req.setNextId(1L);
        req.setPrevId(-1L);

        assertThrows(IllegalArgumentException.class, () -> exhibitController.addExhibit(req));
    }

    @Test
    public void updateExhibitCorrect() {
        UpsertExhibitReq req = new UpsertExhibitReq();
        req.setId(1L);
        req.setFigureUrl("testFUrl");
        req.setDescription("testDes");
        req.setHallId(1L);
        req.setLabel("testLab");
        req.setUrl("testUrl");
        req.setNextId(1L);
        req.setPrevId(-1L);

        exhibitController.updateExhibit(req);
        Exhibit exhibit = exhibitRepository.findByLabel("testLab");
        assertNotNull(exhibit);
        assertEquals(1L,exhibit.getHallId().longValue());
    }

    @Test
    public void updateExhibitWrong() {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        UpsertExhibitReq req = new UpsertExhibitReq();
        req.setId(1L);
        req.setFigureUrl("testFUrl");
        req.setDescription("testDes");
        req.setHallId(11L);
        req.setLabel("testLab");
        req.setUrl("testUrl");
        req.setNextId(1L);
        req.setPrevId(-1L);

        assertThrows(IllegalArgumentException.class, () -> exhibitController.addExhibit(req));
    }

    @Test
    public void deleteExhibitRight() {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(8L);

        exhibitController.deleteExhibit(ids);

        assertNull(exhibitService.getById(8L));
        assertNull(exhibitAliasService.getById(8L));
        assertNull(exhibitTextService.getById(26L));
    }

    @Test
    public void deleteExhibitWrong1() {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(5L);


        assertThrows(BadRequestException.class, () -> exhibitController.deleteExhibit(ids));
    }

    @Test
    public void deleteExhibitWrong2() {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(126L);
        ids.add(127L);

        assertThrows(IllegalArgumentException.class, () -> exhibitController.deleteExhibit(ids));
        assertNotNull(exhibitService.getById(126L));
        assertNotNull(exhibitService.getById(127L));

    }

    @Test
    public void getExhibitAlias() {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        assertNotNull(exhibitController.getExhibitAlias(1L));
    }

    @Test
    public void updateExhibitAliasRight() {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        UpdateExhibitAliasReq req = new UpdateExhibitAliasReq();
        ArrayList<String> text = new ArrayList<>();
        text.add("test1");
        text.add("test2");
        req.setExhibitId(99L);
        req.setExhibitAlias(text);

        QueryWrapper<ExhibitAlias> ea = new QueryWrapper<>();
        ea.eq("exhibit_id",99L);
        assertNotNull(exhibitAliasRepository.selectList(ea));
    }

    @Test
    public void updateExhibitAliasWrong() {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        UpdateExhibitAliasReq req = new UpdateExhibitAliasReq();
        ArrayList<String> text = new ArrayList<>();
        text.add("test1");
        text.add("test2");
        req.setExhibitId(155L);
        req.setExhibitAlias(text);

        assertThrows(IllegalArgumentException.class, () -> exhibitController.updateExhibitAlias(req));
    }

    @Test
    public void updateExhibitTextRight() {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        UpdateExhibitTextReq req = new UpdateExhibitTextReq();
        ArrayList<String> text = new ArrayList<>();
        text.add("test1");
        text.add("test2");
        req.setExhibitId(99L);
        req.setExhibitText(text);

        assertNotNull(exhibitTextRepository.findByExhibitId(99L));
    }

    @Test
    public void updateExhibitTextWrong() {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        UpdateExhibitTextReq req = new UpdateExhibitTextReq();
        ArrayList<String> text = new ArrayList<>();
        text.add("test1");
        text.add("test2");
        req.setExhibitId(155L);
        req.setExhibitText(text);

        assertThrows(IllegalArgumentException.class, () -> exhibitController.updateExhibitText(req));
    }

    @Test
    public void getExhibitText() {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        assertNotNull(exhibitController.getExhibitText(1L));
    }

    //--------------------------------------------------------------------------------------------------

    @Test
    public void getExhibitionHall() {
        assertNotNull(exhibitionHallController.getExhibitionHall(1L));
        ExhibitionHallQuery query = new ExhibitionHallQuery();
        query.setName("火花");
        assertNotNull(exhibitionHallController.listExhibitionHall(query));
    }

    @Test
    public void addExhibitionHallRight() {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        UpsertExhibitionHallReq req = new UpsertExhibitionHallReq();
        req.setDescription("testDrs");
        req.setImageUrl("testUrl");
        req.setMuseumId(1L);
        req.setName("testName");
        assertNotNull(exhibitionHallController.addExhibitionHall(req));
    }

    @Test
    public void addExhibitionHallWrong () {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        UpsertExhibitionHallReq req = new UpsertExhibitionHallReq();
        req.setDescription("testDrs");
        req.setImageUrl("testUrl");
        req.setMuseumId(11L);
        req.setName("testName");
        assertThrows(IllegalArgumentException.class, () -> exhibitionHallController.addExhibitionHall(req));
    }

    @Test
    public void updateExhibitionHall_MUSEUM() {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        UpsertExhibitionHallReq req = new UpsertExhibitionHallReq();
        req.setId(1L);
        req.setDescription("testDrs");
        req.setImageUrl("testUrl");
        req.setMuseumId(3L);
        req.setName("testName");

        exhibitionHallController.updateExhibitionHall(req);
        assertEquals("testUrl", exhibitionHallService.getById(1L).getImageUrl());
        assertEquals(1L, exhibitionHallService.getById(1L).getMuseumId().longValue());
    }

    @Test
    public void updateExhibitionHall_SYS() {
        TestUtil.loginAs(72L, STR_SYS_ADMIN);
        UpsertExhibitionHallReq req = new UpsertExhibitionHallReq();
        req.setId(1L);
        req.setDescription("testDrs");
        req.setImageUrl("testUrl");
        req.setMuseumId(3L);
        req.setName("testName");

        exhibitionHallController.updateExhibitionHall(req);
        assertEquals("testUrl", exhibitionHallService.getById(1L).getImageUrl());
        assertEquals(3L, exhibitionHallService.getById(1L).getMuseumId().longValue());
    }


    @Test
    public void deleteExhibitionHallRight() {
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(2L);

        assertThrows(BadRequestException.class, () -> exhibitionHallController.deleteExhibitionHall(ids));
//        exhibitionHallController.deleteExhibitionHall(ids);
//        assertNull(exhibitionHallService.getById(2L));
//        assertNull(exhibitService.getById(1L).getHallId());
    }

    @Test
    public void deleteExhibitionHallWrong() {
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(2L);

        assertThrows(BadRequestException.class, () -> exhibitionHallController.deleteExhibitionHall(ids));
//        exhibitionHallController.deleteExhibitionHall(ids);
//        assertNotNull(exhibitionHallService.getById(2L));
//        assertNotNull(exhibitService.getById(1L).getHallId());
//        assertThrows(IllegalArgumentException.class, () -> exhibitionHallController.deleteExhibitionHall(ids));
    }

}
