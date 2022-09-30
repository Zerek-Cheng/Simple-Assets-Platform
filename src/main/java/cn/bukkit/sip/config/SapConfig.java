package cn.bukkit.sip.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "web")
@Data
@Component
public class SapConfig {
    private String url;
    private String backend;
    private String casdoorCallback;
    private List<String> allowUpload;
    private String strongeType;
    private Map<String, StrongeConfig> strongeConfig = new HashMap<>();
}
