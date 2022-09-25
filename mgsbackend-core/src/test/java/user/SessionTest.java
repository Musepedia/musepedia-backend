package user;


import com.mimiter.mgs.core.App;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.io.IOUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.RedisSessionRepository;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class SessionTest {

    @Resource
    RedisIndexedSessionRepository redisSessionRepository;

    @Ignore
    @Test
    public void insertSession() {
        SessionRepository sessionRepository = (SessionRepository) redisSessionRepository;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            Session session = sessionRepository.createSession();
            session.setAttribute("userId", 10002L);
            sessionRepository.save(session);
            sb.append(session.getId()).append('\n');
        }
        IOUtil.writeText(sb.toString(), new File("./sessions.txt"));
    }


    private static void generateQuestions() {
        StringBuilder sb = new StringBuilder();
        String[] subjects = {"北极熊", "熊猫", "东北虎", "海星", "海绵宝宝", "虎鲸", "雪鸮", "火花", "驼鹿", "蝴蝶", "企鹅"};
        String[] qs = {"是什么","是什么呢","住在哪里","分布在哪里","分布的地方","长什么样","寿命有多长","可以食用吗"};
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            sb.append(subjects[random.nextInt(subjects.length)])
                    .append(qs[random.nextInt(qs.length)])
                    .append('\n');
        }
        IOUtil.writeText(sb.toString(), new File("./questions.txt"));
    }

    public static void main(String[] args) {
        generateQuestions();
    }
}
