import com.mimiter.mgs.core.App;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = App.class)
@CucumberContextConfiguration
public class SpringbootIntegrated {

}