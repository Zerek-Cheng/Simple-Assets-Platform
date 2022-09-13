package cn.bukkit.sip.orm;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bukkit.sip.orm.mapper.UserMapper;
import cn.bukkit.sip.orm.entity.User;
@Service
public class UserDaoService extends ServiceImpl<UserMapper, User> {

}
