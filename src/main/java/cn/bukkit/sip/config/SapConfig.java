package cn.bukkit.sip.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "web")
@Data
@Component
public class SapConfig {
    private String url;
    private String backend;
    private String casdoorCallback;
    List<String> allowUpload;
}
