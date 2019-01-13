package play.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException {
    private int code;
    private String msg;

    public BizException(BizCode bizCode) {
        code = bizCode.getCode();
        msg = bizCode.getMsg();
    }
}
