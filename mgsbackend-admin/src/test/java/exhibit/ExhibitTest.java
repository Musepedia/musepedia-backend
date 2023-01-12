package exhibit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.admin.App;
import com.mimiter.mgs.admin.controller.ExhibitController;
import com.mimiter.mgs.admin.controller.ExhibitionHallController;
import com.mimiter.mgs.admin.model.query.ExhibitQuery;
import com.mimiter.mgs.admin.model.query.ExhibitionHallQuery;
import com.mimiter.mgs.admin.model.request.*;
import com.mimiter.mgs.admin.repository.ExhibitAliasRepository;
import com.mimiter.mgs.admin.repository.ExhibitRepository;
import com.mimiter.mgs.admin.repository.ExhibitTextRepository;
import com.mimiter.mgs.admin.service.ExhibitAliasService;
import com.mimiter.mgs.admin.service.ExhibitService;
import com.mimiter.mgs.admin.service.ExhibitTextService;
import com.mimiter.mgs.admin.service.ExhibitionHallService;
import com.mimiter.mgs.common.exception.ForbiddenException;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.ExhibitAlias;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
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

    @Test
    public void enableExhibit_MUSEUM() {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        SetEnableReq req = new SetEnableReq();
        req.setId(1L);
        req.setEnable(false);
        exhibitController.enableExhibit(req);
        Exhibit exhibit = exhibitRepository.selectById(1L);
        assertNotNull(exhibit);
        assertEquals(req.getEnable(),exhibit.getEnabled());

        req.setEnable(true);
        exhibitController.enableExhibit(req);
        exhibit = exhibitRepository.selectById(1L);
        assertNotNull(exhibit);
        assertEquals(req.getEnable(),exhibit.getEnabled());
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
        Long id = exhibitionHallController.addExhibitionHall(req).getData();

        ExhibitionHall exhibitionHall = exhibitionHallService.getNotNullById(id);
        assertEquals(1L,exhibitionHall.getMuseumId().longValue());
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
    public void setExhibitionHallEnable_MUSEUM() {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        SetEnableReq req = new SetEnableReq();
        req.setId(2L);
        req.setEnable(false);
        exhibitionHallController.enableExhibitionHall(req);
        assertEquals(req.getEnable(), exhibitionHallService.getById(req.getId()).getEnabled());

        QueryWrapper<Exhibit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exhibition_hall_id",req.getId());
        assertTrue(exhibitRepository.selectList(queryWrapper).stream().allMatch(e -> e.getEnabled().equals(req.getEnable())));

        req.setEnable(true);
        exhibitionHallController.enableExhibitionHall(req);
        assertEquals(req.getEnable(), exhibitionHallService.getById(req.getId()).getEnabled());
        assertTrue(exhibitRepository.selectList(queryWrapper).stream().allMatch(e -> e.getEnabled().equals(req.getEnable())));
    }

    @Test
    public void setExhibitionHallWrong() {
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        SetEnableReq req = new SetEnableReq();
        req.setId(12L);
        req.setEnable(false);
        assertThrows(ForbiddenException.class, () -> exhibitionHallController.enableExhibitionHall(req));
    }

}
