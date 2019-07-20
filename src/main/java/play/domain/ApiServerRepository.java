package play.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import play.domain.entity.ApiServer;

public interface ApiServerRepository extends PagingAndSortingRepository<ApiServer, Integer> {

}
