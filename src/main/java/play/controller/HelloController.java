package play.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import play.config.RollingTimeConfig;

@RestController
public class HelloController {
    @Resource
    private RollingTimeConfig config;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/config")
    public RollingTimeConfig config() {
        return config;
    }
}
