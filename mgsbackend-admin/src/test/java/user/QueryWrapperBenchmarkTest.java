package user;

import com.mimiter.mgs.admin.App;
import com.mimiter.mgs.admin.repository.AdminUserRepository;
import com.mimiter.mgs.admin.service.AdminUserService;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput) // 吞吐量模式
@State(Scope.Benchmark) //使用的SpringBoot容器，都是无状态单例Bean，无安全问题，可以直接使用基准作用域BenchMark
@OutputTimeUnit(TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2) //正式测试5轮
@Warmup(iterations = 1, time = 1) //预热1轮
@Fork(1) //每轮测试都fork一次
public class QueryWrapperBenchmarkTest {

    //springBoot容器
    private ApplicationContext context;

    private AdminUserService userService;

    private AdminUserRepository userRepository;

    /**
     * 初始化，获取springBoot容器，run即可，同时得到相关的测试对象
     */
    @Setup
    public void init() {
        //容器获取
        context = SpringApplication.run(App.class);
        //获取对象
        userService = context.getBean(AdminUserService.class);
        userRepository = context.getBean(AdminUserRepository.class);
    }

    @Benchmark
    public void useQueryWrapper() {
        userService.findByUsername("mgs_admin");
    }


    @Benchmark
    public void useMybatisSql() {
        userRepository.findByUsername("mgs_admin");
    }

    /**
     * 测试的后处理操作，关闭容器，资源清理
     */
    @TearDown
    public void down() {
        ((ConfigurableApplicationContext)context).close();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + QueryWrapperBenchmarkTest.class.getSimpleName() + ".*")
                .build();
        new Runner(opt).run();
    }
}
