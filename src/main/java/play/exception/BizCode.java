package play.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum BizCode {
    // 错误码定义
    INVALID_REQUEST(4000, "无效的请求"),
    PARAM_ERROR(4001, "请求参数错误"),

    PRIZE_IS_EMPTY(4101, "奖励已领完"),
    PRIZE_IS_EMPTY_WAIT_NEXT_ROUND(4101, "奖励已领完，请等待下一轮发放"),
    ;

    private int code;
    private String msg;

    BizCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
