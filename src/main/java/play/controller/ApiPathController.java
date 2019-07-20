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
import play.domain.entity.ApiPath;

@RestController
@RequestMapping("/entity/apiPath/")
public class ApiPathController {

    @Resource
    private ApiPathRepository apiPathRepository;

    @PostMapping("save")
    public ApiPath save(@RequestBody ApiPath api) {
        return apiPathRepository.save(api);
    }

    @GetMapping("findById")
    public ApiPath findById(@RequestParam Integer id) {
        return apiPathRepository.findById(id).orElse(null);
    }

    @GetMapping("findAll")
    public Iterable<ApiPath> findAll() {
        return apiPathRepository.findAll(new Sort(Direction.DESC, "id"));
    }
}
