package play.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import play.domain.entity.CachePath;

public interface CachePathRepository extends PagingAndSortingRepository<CachePath, Integer> {

}
