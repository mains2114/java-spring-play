package play.config;

import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * @author huang
 */
@Configuration
public class RestConfig {

    @Bean
    public RestTemplate initRest() {
        RestTemplate tpl = new RestTemplate();
        tpl.getMessageConverters().forEach(e -> {
            if (e instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) e).setDefaultCharset(StandardCharsets.UTF_8);
            }
        });
        return tpl;
    }
}
