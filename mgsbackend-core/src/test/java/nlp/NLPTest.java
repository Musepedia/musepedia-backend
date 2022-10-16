package nlp;

import com.mimiter.mgs.core.App;
import com.mimiter.mgs.core.service.ExhibitTextService;
import com.mimiter.mgs.core.utils.NLPUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class NLPTest {

    private static String[] subjects = {"澳洲鸵鸟","鸵鸟","麋","犴","罕达犴","堪达犴","小鼷鹿","鼠鹿","小跳麂","马来亚鼷鹿","小鹿","改范（傣）","蛱蝶","胡蝶","浮蝶儿","白熊","白狼","蓝狐","白狐","山兔","蓝兔","角鹿","麝香牛","北极麝牛","囊鼻海豹","贝鲁卡鲸","海金丝雀 ","北极鲸","格陵兰露脊鲸","巨极地鲸","北露脊鲸","格陵兰鲸","雪枭","白猫头鹰","白鸮","雪鹰","雷鸟","柳鸡","苏衣尔","雪鸡","南极雪海燕","雪圆尾鸌","信天公","信天缘","剃刀鲸","逆戟鲸","僧海豹","威德尔海豹","威氏海豹","威德尔氏海豹","亚归","蒙古野驴","长毛羊","塔尔羊","西伯利亚北山羊","亚洲羚羊","红羊","悬羊","喜马拉雅山羊","亚洲野山羊","盘角羊","大角羊","大头羊","崖羊","半羊","石羊","青羊","山盘羊","盘羊","兰羊","欠那","那瓦","贡那","西城区","红斑羚","红山羊","红青羊","普通长鼻犰狳","南美貘","巴西貘","考拉","无尾熊","可拉熊","树懒熊","狮","狻猊","鳄","赤足鹬","东方红腿","白鹭","白鹭鸶","白翎鸶","春锄","雪客","黄头鹭","畜鹭","放牛郎","花鹿，鹿","中华角石","恐鱼","棘背龙","脊背龙","石笔海胆","美洲剑齿虎","阴沉木","胡桐","英雄树","异叶胡杨","异叶杨","水桐","三叶树","野狼","豺狼","灰狼","老虎","黑熊","月熊","月牙熊","狗熊","黑瞎子","灰熊","猫熊","竹熊","银狗","洞尕","杜洞尕","执夷","貊","猛豹","食铁兽","北美黑熊","蜜熊","安第斯熊","狗熊","太阳熊","小狗熊","小黑熊","耐力喀苏（藏）","日熊","云南仰鼻猴","反鼻猴","黑金丝猴","黑仰鼻猴","狮子鼻猴","仰鼻猴","金狨猴","兰面猴","洛克安娜猴等","黑长臂猿","印支长臂猿","冠长臂猿","红毛猩猩","人猿","红猩猩","尼斯湖水怪","豆腐鲨","大憨鲨","大白鲨","白鲨","食人鲛","白死鲨","白鲛","食人鲨","鳀鲸","拟大须鲸","亚洲朱鹮","日本朱鹮","鸵鸟","仙鹤","红冠鹤","金鹫","老雕","洁白雕","鹫雕","大胡子雕"};

    @Resource
    private ExhibitTextService exhibitTextService;

    @Test
    public void testExhibitService() {
        String question = "阿拉伯婆婆纳的寿命如何";
        System.out.println(exhibitTextService.getAllTexts(question, 2L));
        System.out.println(exhibitTextService.getAllTexts(question, 1L));
    }

    @Test
    @SneakyThrows
    public void withoutBlockingQueue(){
        List<String> labels = new ArrayList<>();
        for (int i=0; i<subjects.length; ++i) {
            labels.add(subjects[i]);
        }
        CountDownLatch l = new CountDownLatch(subjects.length);
        for (int i = 0; i < subjects.length; i++) {
            int finalI = i;
            new Thread(() -> {
                try{
                    exhibitTextService.getLabel(labels, subjects[finalI] + "是什么");
                    // exhibitTextService.getAllTexts(subjects[finalI] + "怎么样", 2L);
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    l.countDown();
                }
            }).start();
        }
        l.await();
    }
}
