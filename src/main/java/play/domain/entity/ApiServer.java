package play.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ApiServer {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String env;
    private String baseUri;
}
