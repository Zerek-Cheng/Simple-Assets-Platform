package cn.bukkit.sip.service;

import cn.bukkit.sip.config.SapConfig;
import cn.bukkit.sip.orm.entity.ImgEntity;
import cn.bukkit.sip.stronge.SapStronge;
import cn.bukkit.sip.stronge.local.LocalFileStronge;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@Component
@Slf4j
public class StrongeService {
    @Resource
    SapConfig sapConfig;
    private Map<String, Class<? extends SapStronge>> strongeClazz = new HashMap<>() {
        {
            put("local-file", LocalFileStronge.class);
        }
    };

    private Map<String, SapStronge> strongeMap = new HashMap<>();

    @PostConstruct
    public void init() {
        this.sapConfig.getStrongeConfig().forEach((sName, sConfig) -> {
            if (!sConfig.isEnabled()) return;
            try {
                Class<? extends SapStronge> strongeClass = strongeClazz.get(sConfig.getType());
                log.info("Registering the stronge modules: {} ({})", sName, strongeClass);
                Constructor<? extends SapStronge> constructor = strongeClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                SapStronge sapStronge = constructor.newInstance();
                sapStronge.init(sName, sConfig);
                strongeMap.put(sName, sapStronge);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SapStronge getDefault() {
        return this.strongeMap.get(this.sapConfig.getStrongeType());
    }

    public SapStronge getImgStronge(ImgEntity imgEntity) {
        return Optional.ofNullable(this.strongeMap.get(imgEntity.getStronge())).orElse(this.getDefault());
    }
}
