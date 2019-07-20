package play.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = {"apiServerId", "method", "uri"}),
    indexes = {
        @Index(name = "idx_uri", columnList = "uri")
    })
public class ApiPath {
    @Id
    @GeneratedValue
    private Integer id;
    private String uri;
    private String method;
    private String summary;
    private String description;
    private String parameter;
    private String response;
    private Integer apiServerId;
}
