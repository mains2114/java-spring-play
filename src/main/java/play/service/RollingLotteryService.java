package play.service;

import javax.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import play.config.RollingTimeConfig;
import play.config.RollingTimeConfig.Round;
import play.exception.BizCode;
import play.exception.BizException;

@Component
public class RollingLotteryService {

    @Resource
    private RollingTimeConfig config;

    @Resource
    private StringRedisTemplate redis;

    public Long getLottery() {
        Round round = config.getCurrentRound();

        String key = round.getCacheHashKey();
        Integer result = Integer.valueOf(redis.opsForValue().get(key).toString());
        if (result > round.getAmount()) {
            throw new BizException(
                config.isLastRound(round) ? BizCode.PRIZE_IS_EMPTY : BizCode.PRIZE_IS_EMPTY_WAIT_NEXT_ROUND);
        }

        Long result2 = redis.opsForValue().increment(key);
        if (result2 > round.getAmount()) {
            throw new BizException(
                config.isLastRound(round) ? BizCode.PRIZE_IS_EMPTY : BizCode.PRIZE_IS_EMPTY_WAIT_NEXT_ROUND);
        }

        return result2;
    }
}
