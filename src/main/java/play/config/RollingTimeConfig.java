package play.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "rolling")
@Component
public class RollingTimeConfig {
    private Date start;
    private Date end;
    private Long interval;
    private Long amount;

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void setStart(String start) throws ParseException {
        this.start = DATE_FORMAT.parse(start);
    }

    public void setEnd(String end) throws ParseException {
        this.end = DATE_FORMAT.parse(end);
    }

    @Data
    public class Round {
        private Date start;
        private Date end;
        private Long amount;

        public String getCacheHashKey() {
            return "";
        }
    }

    public List<Round> getRollingRounds() {
        Long startTs = start.getTime();
        Long intervalMs = interval * 1000;
        Long roundCnt = (end.getTime() - start.getTime()) / intervalMs;
        Long averageAmount = amount / roundCnt;

        ArrayList<Round> list = new ArrayList<>();
        while (startTs < end.getTime()) {
            Round tmpRound = new Round();
            tmpRound.setStart(new Date(startTs));
            tmpRound.setEnd(new Date(startTs + interval * 1000));
            tmpRound.setAmount(averageAmount);

            list.add(tmpRound);
            startTs += interval * 1000;
        }

        return list;
    }

    public Round getCurrentRound() {
        Long ts = System.currentTimeMillis();

        Round round = this.getRollingRounds().stream()
            .filter(e -> e.getStart().getTime() <= ts && e.getEnd().getTime() > ts).findFirst().orElse(null);
        if (round == null) {
            throw new RuntimeException("活动未开始");
        }

        return round;
    }

    public boolean isLastRound(Round round) {
        return round.getEnd().getTime() >= end.getTime();
    }
}
