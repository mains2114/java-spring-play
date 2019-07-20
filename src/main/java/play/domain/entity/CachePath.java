package play.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CachePath {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer type;
    private String key;
    private String value;
    private String hKey;
    private String hVal;
    private String score;
    private String summary;

    public static final int TYPE_STRING = 1;
    public static final int TYPE_HASH = 2;
    public static final int TYPE_LIST = 3;
    public static final int TYPE_SET = 4;
    public static final int TYPE_ZSET = 5;
}
