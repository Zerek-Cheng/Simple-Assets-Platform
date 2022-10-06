package cn.bukkit.sip.service;

import cn.bukkit.sip.config.SapConfig;
import cn.bukkit.sip.orm.entity.ImgEntity;
import cn.bukkit.sip.storage.SapStorage;
import cn.bukkit.sip.storage.local.LocalFileStorage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
                Class<? extends SapStorage> strongeClass = strongeClazz.get(sConfig.getType());
                log.info("Registering the stronge modules: {} ({})", sName, strongeClass);
                Constructor<? extends SapStorage> constructor = strongeClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                SapStorage sapStorage = constructor.newInstance();
                sapStorage.init(sName, sConfig);
                strongeMap.put(sName, sapStorage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Resource
    SapConfig sapConfig;
    private Map<String, Class<? extends SapStorage>> strongeClazz = new HashMap<>() {
        {
            put("local-file", LocalFileStorage.class);
        }
    };

    private Map<String, SapStorage> strongeMap = new HashMap<>();

    public SapStorage getDefault() {
        return this.strongeMap.get(this.sapConfig.getStorageType());
    }

    public SapStorage getImgStronge(ImgEntity imgEntity) {
        return Optional.ofNullable(this.strongeMap.get(imgEntity.getStorage())).orElse(this.getDefault());
    }

}
