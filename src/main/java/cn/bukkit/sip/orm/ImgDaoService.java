package cn.bukkit.sip.orm;

import cn.bukkit.sip.orm.entity.ImgEntity;
import cn.bukkit.sip.orm.mapper.ImgMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class ImgDaoService extends ServiceImpl<ImgMapper, ImgEntity> {
    @Override
    @Cacheable(value = "img", key = "#id", unless = "#result == null")
    public ImgEntity getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    @CacheEvict(value = "img", key = "#imgEntity.id")
    public boolean updateById(ImgEntity imgEntity) {
        return super.updateById(imgEntity);
    }
}
