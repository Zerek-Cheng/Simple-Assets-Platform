package cn.bukkit.sip.config;

import lombok.Data;

@Data
public class StrongeConfig {
    private boolean enabled;
    private String type;
    private String path;
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String domain;
}
