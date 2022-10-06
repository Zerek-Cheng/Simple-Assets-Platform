package cn.bukkit.sip.service;

import cn.bukkit.sip.config.SapConfig;
import cn.bukkit.sip.exception.StorageNotExistException;
import cn.bukkit.sip.orm.entity.ImgEntity;
import cn.bukkit.sip.storage.SapStorage;
import cn.bukkit.sip.storage.http.QiniuStorage;
import cn.bukkit.sip.storage.local.LocalFileStorage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@Component
@Slf4j
public class StorageService implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.sapConfig.getStorageConfig().forEach((sName, sConfig) -> {
            if (!sConfig.isEnabled()) return;
            try {
                Class<? extends SapStorage> storageClazz = this.storageClazz.get(sConfig.getType());
                log.info("Registering the storage modules: {} ({})", sName, storageClazz);
                Constructor<? extends SapStorage> constructor = storageClazz.getConstructor();
                SapStorage sapStorage = constructor.newInstance();
                if (sapStorage.init(sName, sConfig))
                    storageMap.put(sName, sapStorage);
            } catch (Exception e) {
                log.error("Registering the storage modules: {} ({}) failed", sName, e.toString());
            }
        });
    }

    @Resource
    SapConfig sapConfig;
    private Map<String, Class<? extends SapStorage>> storageClazz = new HashMap<>() {
        {
            put("local-file", LocalFileStorage.class);
            put("qiniu", QiniuStorage.class);
        }
    };

    private Map<String, SapStorage> storageMap = new HashMap<>();

    public SapStorage getDefault() {
        return this.storageMap.get(this.sapConfig.getStorageType());
    }

    public SapStorage getStorage(String name) {
        return this.storageMap.get(name);
    }

    public SapStorage getStorage(ImgEntity imgEntity) {
        return this.getStorage(imgEntity.getStorage());
    }

    /**
     * 检查资源是否存在，此处使用缓存
     */
    @Cacheable(value = "img-exist", key = "#imgEntity.id+'/60'")
    public boolean isFileExist(ImgEntity imgEntity) {
        return Optional.ofNullable(this.getStorage(imgEntity)).orElseThrow(StorageNotExistException::new).isExist(imgEntity.getPath());
    }

}
