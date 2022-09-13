package cn.bukkit.sip.orm;

import cn.bukkit.sip.orm.entity.Img;
import cn.bukkit.sip.orm.mapper.ImgMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class ImgDaoService extends ServiceImpl<ImgMapper, Img> {
    @Override
    @Cacheable(value = "img", key = "#id", unless = "#result == null")
    public Img getById(Serializable id) {
        System.out.println(111);
        return super.getById(id);
    }

}
