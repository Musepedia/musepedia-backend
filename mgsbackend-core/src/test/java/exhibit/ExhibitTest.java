package exhibit;

import com.mimiter.mgs.core.App;
import com.mimiter.mgs.core.service.ExhibitService;
import com.mimiter.mgs.core.service.LuceneSearchService;
import com.mimiter.mgs.model.entity.ExhibitText;
import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
public class ExhibitTest {

    @Resource
    private ExhibitService exhibitService;

    @Resource
    private LuceneSearchService luceneSearchService;

    @Test
    public void testSelectExhibit() {
        System.out.println(exhibitService.selectExhibitByLabelAndMuseumId("阴丹士林", 3L));
    }

    @Test
    public void testUpdateLucene() throws SQLException, IOException {
        luceneSearchService.updateIndex();
    }

    @Test
    public void testSearchOnText() throws IOException, ParseException {
        // 取交集，加强约束
        List<ExhibitText> texts = new ArrayList<>(luceneSearchService.queryOnText("狼分布在哪里", 1L));
        for (ExhibitText text : texts) {
            System.out.println(text);
        }
        System.out.println(texts.size());
        texts.retainAll(luceneSearchService.queryOnLabel("狼分布在哪里", 1L));

        System.out.println("****************   加强约束的结果   *****************");

        for (ExhibitText text : texts) {
            System.out.println(text);
        }
    }

    @Test
    public void testSearchOnAlias() throws IOException, ParseException {
        for (ExhibitText text : luceneSearchService.queryOnAlias("钱学森是谁", 4L)) {
            System.out.println(text);
        }
    }

    @Test
    public void testSearchOnLabel() throws IOException, ParseException {
        for (ExhibitText text : luceneSearchService.queryOnLabel("火花是什么", 3L)) {
            System.out.println(text);
        }
    }

    @Test
    public void testGetTopKTexts() throws IOException, ParseException {
        long millis1 = System.currentTimeMillis();

        for (ExhibitText text : luceneSearchService.getTopKTexts("钱学森是谁", 4L, 5)) {
            System.out.println(text);
        }

        long millis2 = System.currentTimeMillis();
        System.out.println(millis2 - millis1);//经过的毫秒数
    }
}
