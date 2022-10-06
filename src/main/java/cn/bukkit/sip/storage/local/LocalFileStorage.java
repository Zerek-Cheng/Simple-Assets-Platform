package cn.bukkit.sip.storage.local;

import cn.bukkit.sip.config.StorageConfig;
import cn.bukkit.sip.exception.UploadException;
import cn.bukkit.sip.storage.LocalStorage;
import cn.hutool.core.io.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalFileStorage extends LocalStorage {
    String localPath;
    String name = "服务端存储";

    @Override
    public boolean init(String name, StorageConfig config) {
        super.init(name, config);
        this.localPath = config.getPath();
        return true;
    }

    @Override
    public File saveFile(String path, byte[] data) {
        File file = new File(localPath + path);
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        if (file.exists()) throw new UploadException("文件已经存在");
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

}
