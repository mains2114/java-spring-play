package play.service.mhy;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MhyBbsApiServiceTest {

    @Resource
    private MhyBbsApiService mhyBbsApiService;

    @Test
    public void getUserGameRolesByCookie() {
        String res = mhyBbsApiService.getUserGameRolesByCookie();
        log.info("res = {}", res);
    }
}