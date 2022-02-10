package play.service.mhy.impl;

import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import play.service.mhy.MhyBbsApiService;

/**
 * @author huang
 */
@Service
@Slf4j
public class MhyBbsApiServiceImpl implements MhyBbsApiService {

    @Resource
    private RestTemplate restTemplate;

    private static final Gson GSON = new Gson();

    @Value("${MhyBbsApiService.host:https://api-takumi.mihoyo.com}")
    private String host;

    @Value("${MhyBbsApiService.salt:xV8v4Qu54lUKrEYFZkJhB8cuOh9Asafs}")
    private String salt;

    @Value("${MhyBbsApiService.cookie:xV8v4Qu54lUKrEYFZkJhB8cuOh9Asafs}")
    private String cookie;

    private String calcSign(Map<String, Object> query, Map<String, Object> body) {
        long ts = System.currentTimeMillis() / 1000;
        long rand = (long) (Math.random() * 100000) + 100000;

        String queryStr = buildQueryStr(query);
        String bodyStr = body.size() > 0 ? GSON.toJson(body) : "";
        String signStr = MessageFormat.format("salt={}&t={}&r={}&b={}&q={}",
            salt, ts, rand, bodyStr, queryStr);
        String signVal = md5(signStr);

        return MessageFormat.format("{},{},{}", ts, rand, signVal);
    }

    private static String buildQueryStr(Map<String, Object> query) {
        StringBuilder querySb = new StringBuilder();
        if (query.size() > 0) {
            query.keySet().stream().sorted().forEachOrdered(key -> {
                Object value = query.get(key);
                querySb.append(key).append("=").append(value).append("&");
            });
        }
        querySb.deleteCharAt(querySb.length() - 1);

        return querySb.toString();
    }

    private static String md5(String input) {
        MessageDigest instance = null;
        try {
            instance = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            log.error("md5 fail", e);
            return "";
        }

        byte[] result = instance.digest(input.getBytes(StandardCharsets.UTF_8));
        return new String(result);
    }

    private HttpEntity<String> buildEntity(String sign, String refer) {
        String version = "2.19.1";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.55 Safari/537.36 miHoYoBBS/" + version);
        headers.add("X-Requested-With", "com.mihoyo.hyperion");
        headers.add("Cookie", cookie);
        if (refer != null) {
            headers.add("Referer", refer);
        } else {
            headers.add("Referer", "https://webstatic.mihoyo.com/app/community-game-records/index.html?v=6");
        }
        if (!"".equals(sign)) {
            headers.add("DS", sign);
            headers.add("x-rpc-app_version", "2.19.1");
            headers.add("x-rpc-client_type", "5");
        }

        return new HttpEntity<>("", headers);
    }


    @Override
    public String getUserGameRolesByCookie() {
        HashMap<String, Object> query = new HashMap<>(10);
        query.put("game_biz", "hk4e_cn");

        String ds = calcSign(query, Collections.emptyMap());
        HttpEntity<String> entity = buildEntity(ds, null);

        String url = host + "/binding/api/getUserGameRolesByCookie?" + buildQueryStr(query);
        try {
            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return result.getBody();
        } catch (Exception e) {
            log.error("getUserGameRolesByCookie fail, url={}", url, e);
        }

        return "";
    }
}
