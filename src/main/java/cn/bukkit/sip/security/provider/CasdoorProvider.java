package cn.bukkit.sip.security.provider;

import cn.bukkit.sip.security.token.CasdoorAuthenticationToken;
import cn.bukkit.sip.security.token.CasdoorSapPrincipal;
import org.casbin.casdoor.entity.CasdoorUser;
import org.casbin.casdoor.service.CasdoorAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class CasdoorProvider implements AuthenticationProvider {
    @Autowired
    private CasdoorAuthService casdoorAuthService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!this.supports(authentication.getClass()))
            throw new BadCredentialsException("Authentication type not supported");
        CasdoorAuthenticationToken token = (CasdoorAuthenticationToken) authentication;
        String tokenStr = casdoorAuthService.getOAuthToken(token.getCode(), token.getState());
        CasdoorUser casdoorUser = casdoorAuthService.parseJwtToken(tokenStr);
        if (token.getAuthorities() == null) {
            token.setAuthorities(new ArrayList<>());
        }
        casdoorUser.getRoles().forEach(role -> {
            token.getAuthorities().add(new SimpleGrantedAuthority(role.getName()));
            for (String subRole : role.getRoles()) {
                token.getAuthorities().add(new SimpleGrantedAuthority(subRole));
            }
        });
        token.setPrincipal(new CasdoorSapPrincipal(casdoorUser));
        token.setCredentials(UUID.randomUUID().toString());
        token.setAuthenticated(true);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CasdoorAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
