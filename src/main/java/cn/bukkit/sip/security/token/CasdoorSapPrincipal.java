package cn.bukkit.sip.security.token;

import lombok.Data;
import org.casbin.casdoor.entity.CasdoorUser;

import java.util.Arrays;

@Data
public class CasdoorSapPrincipal extends CasdoorUser implements SapPrincipal {
    CasdoorUser casdoorUser;

    public CasdoorSapPrincipal(CasdoorUser user) {
        this.casdoorUser = user;
        Arrays.stream(user.getClass().getDeclaredFields()).forEach(field -> {
            try {
                field.setAccessible(true);
                field.set(this, field.get(user));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }


}
