package play.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import play.domain.entity.DbTable;

public interface DbTableRepository extends PagingAndSortingRepository<DbTable, Integer> {

}
