package play.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class DbTable {
    @Id
    @GeneratedValue
    private Integer id;
    private String host;
    private String port;
    private String db;
    private String table;
    private String summary;
}
