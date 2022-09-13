package cn.bukkit.sip.orm.mapper;

import cn.bukkit.sip.orm.entity.Img;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;

@Mapper
public interface ImgMapper extends BaseMapper<Img> {
}