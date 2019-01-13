package play;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import play.config.RollingTimeConfig;
import play.config.RollingTimeConfig.Round;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class JavaSpringPlayApplicationTests {
    @Resource
    private RollingTimeConfig config;

    @Test
    public void contextLoads() {
    }

    @Test
    public void rollingLottery() {
        log.info(config.toString());

        for (Round r: config.getRollingRounds()) {
            log.info(r.toString());
        }
    }
}

