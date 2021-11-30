package example;

import cn.abstractmgs.core.service.RecommendQuestionService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

@Slf4j
public class ExampleStepDefs {

    @Resource
    RecommendQuestionService recommendQuestionService;

    @And("I click the {string} button")
    public void iClickTheButton(String arg0) {
        log.info("Inject test: " + (recommendQuestionService != null));
    }

    @Given("I am on the {string} page")
    public void iAmOnThePage(String arg0) {

    }

    @Then("I should go to the {string} page")
    public void iShouldGoToThePage(String arg0) {
    }
}
