package play.controller;

import javax.annotation.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import play.domain.ApiPathRepository;
import play.domain.ApiServerRepository;
import play.domain.CachePathRepository;
import play.domain.DbTableRepository;
import play.domain.entity.DbTable;

@RestController
@RequestMapping("/entity/apiServer")
public class DbTableController {

    @Resource
    private ApiPathRepository apiPathRepository;

    @Resource
    private ApiServerRepository apiServerRepository;

    @Resource
    private CachePathRepository cachePathRepository;

    @Resource
    private DbTableRepository dbTableRepository;

    @PostMapping("save")
    public DbTable save(@RequestBody DbTable api) {
        return dbTableRepository.save(api);
    }

    @GetMapping("findById")
    public DbTable findById(@RequestParam Integer id) {
        return dbTableRepository.findById(id).orElse(null);
    }

    @GetMapping("findAll")
    public Iterable<DbTable> findAll() {
        return dbTableRepository.findAll(new Sort(Direction.DESC, "id"));
    }
}
