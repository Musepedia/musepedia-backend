package preference;

import com.mimiter.mgs.core.App;
import com.mimiter.mgs.core.controller.ExhibitController;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import com.mimiter.mgs.core.model.entity.User;
import com.mimiter.mgs.core.repository.ExhibitRepository;
import com.mimiter.mgs.core.repository.ExhibitionHallRepository;
import com.mimiter.mgs.core.repository.UserRepository;
import com.mimiter.mgs.core.service.ExhibitService;
import com.mimiter.mgs.core.service.UserPreferenceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class PreferenceTest {

    @Resource
    ExhibitService exhibitService;

    @Resource
    ExhibitionHallRepository exhibitionHallRepository;

    @Resource
    ExhibitRepository exhibitRepository;

    @Resource
    UserPreferenceService userPreferenceService;

    @Resource
    UserRepository userRepository;

    @Resource
    ExhibitController exhibitController;

    private List<ExhibitionHall> halls;

    private User user;

    private final int hallAmount = 22;

    @Before
    public void insertData(){
        // insert user
        user = new User();
        user.setAvatarUrl("");
        user.setNickname("TestUser");
        user.setPhoneNumber("");
        userRepository.insert(user);

        // insert exhibition hall
        halls = new ArrayList<>();
        for (int i = 0; i < hallAmount; i++) {
            ExhibitionHall hall = new ExhibitionHall();
            hall.setName("HallName " + i);
            hall.setDescription("HallDescription " + i);
            exhibitionHallRepository.insert(hall);
            halls.add(hall);
        }

        // insert exhibit
        for (int i = 0; i < hallAmount * 3; i++) {
            Exhibit exhibit = new Exhibit();
            exhibit.setLabel("ExhibitLabel " + i);
            exhibit.setHallId(halls.get(i % hallAmount).getId());
            exhibit.setDescription("ExhibitDescription " + i);
            exhibitRepository.insert(exhibit);
        }
    }

    @Test
    public void updateUserPreference(){
        List<Long> hallIds = new ArrayList<>();
        hallIds.add(halls.get(0).getId());
        hallIds.add(halls.get(2).getId());
        hallIds.add(halls.get(6).getId());
        userPreferenceService.updateUserPreference(user.getId(), hallIds, 1L);

        List<ExhibitionHall> preferredHalls = userPreferenceService.getPreferredHallByUserId(user.getId(), 1L);

        List<Long> newHallIds = new ArrayList<>();
        newHallIds.add(halls.get(0).getId());
        newHallIds.add(halls.get(10).getId());
        userPreferenceService.updateUserPreference(user.getId(), newHallIds, 1L);

        List<ExhibitionHall> newPreferredHalls = userPreferenceService.getPreferredHallByUserId(user.getId(), 1L);

    }
}
