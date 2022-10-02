package cn.bukkit.sip.stronge;

import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

public abstract class HttpStronge implements NetStronge {
    @Resource
    RestTemplate httpClient;

    @Override
    public RestTemplate getHttpClient() {
        return httpClient;
    }
}
