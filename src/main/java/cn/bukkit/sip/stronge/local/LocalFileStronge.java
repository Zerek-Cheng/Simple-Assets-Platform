package cn.bukkit.sip.stronge.local;

import cn.bukkit.sip.stronge.LocalStronge;
import cn.hutool.core.io.FileUtil;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
@ConditionalOnProperty(prefix = "web", name = "stronge-type", havingValue = "local-file", matchIfMissing = true)
@ConfigurationProperties(prefix = "web.stronge-config.local-file")
public class LocalFileStronge extends LocalStronge {
    String localPath;

    @Override
    public String getName() {
        return "LocalFileStronge";
    }

    @Override
    public File saveFile(String path, byte[] data) {
        File file = new File(localPath + path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) throw new RuntimeException("文件已经存在");
        file = FileUtil.writeBytes(data, file);
        return file.exists() ? file : null;
    }

    @Override
    public boolean isExist(String path) {
        return FileUtil.exist(localPath + path);
    }

    @Override
    public byte[] getFile(String path) {
        return FileUtil.readBytes(localPath + path);
    }

    @Override
    public boolean deleteFile(String path) {
        return FileUtil.del(localPath + path);
    }

    @Override
    public Map<String, String> getConfig() {
        return new HashMap<>() {
            {
                put("localPath", localPath);
            }
        };
    }
}
