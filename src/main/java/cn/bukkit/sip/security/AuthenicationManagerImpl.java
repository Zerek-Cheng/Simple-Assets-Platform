package cn.bukkit.sip.security;

import cn.bukkit.sip.security.provider.CasdoorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.stereotype.Component;

@Component
public class AuthenicationManagerImpl extends ProviderManager {

    public AuthenicationManagerImpl(@Autowired CasdoorProvider casdoorProvider) {
        super(casdoorProvider);
    }
}
