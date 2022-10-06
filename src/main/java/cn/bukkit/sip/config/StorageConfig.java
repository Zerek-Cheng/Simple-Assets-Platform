package cn.bukkit.sip.config;

import lombok.Data;

import java.util.HashMap;

@Data
public class StorageConfig {
    private boolean enabled;
    private String type;
    private String path;
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String domain;
    private HashMap<String, String> meta;
}
