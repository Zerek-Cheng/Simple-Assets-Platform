package cn.bukkit.sip.orm;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bukkit.sip.orm.mapper.UserMapper;
import cn.bukkit.sip.orm.entity.User;
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

}
