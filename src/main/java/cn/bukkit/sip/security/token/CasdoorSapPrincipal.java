package cn.bukkit.sip.security.token;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import org.casbin.casdoor.entity.CasdoorUser;

import java.util.Arrays;

@Data
public class CasdoorSapPrincipal extends CasdoorUser implements SapPrincipal {
    CasdoorUser casdoorUser;

    public CasdoorSapPrincipal(CasdoorUser user) {
        this.casdoorUser = user;
        BeanUtil.copyProperties(user,this,true);
    }


}
