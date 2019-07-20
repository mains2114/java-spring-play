package play.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import play.domain.entity.ApiPath;

public interface ApiPathRepository extends PagingAndSortingRepository<ApiPath, Integer> {

}
