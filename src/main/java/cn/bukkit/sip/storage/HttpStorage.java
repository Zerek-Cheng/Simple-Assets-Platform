package cn.bukkit.sip.storage;

import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

public abstract class HttpStorage implements NetStorage {
    @Resource
    RestTemplate httpClient;

    @Override
    public RestTemplate getHttpClient() {
        return httpClient;
    }
}
