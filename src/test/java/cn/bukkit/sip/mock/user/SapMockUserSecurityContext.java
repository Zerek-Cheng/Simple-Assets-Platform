package cn.bukkit.sip.mock.user;

import cn.bukkit.sip.security.token.CasdoorSapPrincipal;
import cn.bukkit.sip.security.token.SapToken;
import cn.hutool.core.util.RandomUtil;
import org.casbin.casdoor.entity.CasdoorUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class SapMockUserSecurityContext implements Serializable, SecurityContext {
    Authentication auth = new SapToken() {
        final String mockUserName = RandomUtil.randomString(6);
        final CasdoorSapPrincipal casdoorSapPrincipal = new CasdoorSapPrincipal(new CasdoorUser() {
            @Override
            public String getName() {
                return mockUserName;
            }

            @Override
            public String getId() {
                return UUID.randomUUID().toString();
            }

            @Override
            public String getOwner() {
                return "mock";
            }
        });

        @Override
        public CasdoorSapPrincipal getPrincipal() {
            return this.casdoorSapPrincipal;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return new ArrayList<>();
        }

        @Override
        public Object getCredentials() {
            return UUID.randomUUID();
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return this.mockUserName;
        }
    };

    @Override
    public Authentication getAuthentication() {
        return this.auth;
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        this.auth = authentication;
    }
}
