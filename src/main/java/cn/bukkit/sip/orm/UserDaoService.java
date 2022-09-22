package cn.bukkit.sip.orm;

import cn.bukkit.sip.orm.entity.UserEntity;
import cn.bukkit.sip.orm.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
@Service
public class UserDaoService extends ServiceImpl<UserMapper, UserEntity> {

}
