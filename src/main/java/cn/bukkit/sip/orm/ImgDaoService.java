package cn.bukkit.sip.orm;

import cn.bukkit.sip.orm.entity.ImgEntity;
import cn.bukkit.sip.orm.mapper.ImgMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;

@Service
public class ImgDaoService extends ServiceImpl<ImgMapper, ImgEntity> {
    public static final String KEY_USER_TOTAL = "img-user-total::";

    @Override

    @Caching(evict = {
            @CacheEvict(cacheNames = KEY_USER_TOTAL, key = "#entity.owner"),
            @CacheEvict(value = "img", key = "#entity.id")
    })
    public boolean saveOrUpdate(ImgEntity entity) {
        return super.saveOrUpdate(entity);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = KEY_USER_TOTAL, key = "#entity.owner"),
            @CacheEvict(value = "img", key = "#entity.id")
    })
    public boolean save(ImgEntity entity) {
        return super.save(entity);
    }

    @Override
    @Cacheable(value = "img", key = "#id", unless = "#result==null")
    public ImgEntity getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    @CacheEvict(value = "img", key = "#imgEntity.id")
    public boolean updateById(ImgEntity imgEntity) {
        return super.updateById(imgEntity);
    }

    @Override
    public boolean updateBatchById(Collection<ImgEntity> entityList) {
        entityList.forEach(this::updateById);
        return super.updateBatchById(entityList);
    }

    @Override
    @CacheEvict(value = "img-exist", key = "#id")
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @CacheEvict(value = "img", key = "#id")
    public void cleanCache(Serializable id) {
    }
}
