package cn.bukkit.sip.mock.user;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class SapMockUserContextFactory implements WithSecurityContextFactory<WithSapMockUser> {
    @Override
    public SecurityContext createSecurityContext(WithSapMockUser annotation) {
        return new SapMockUserSecurityContext();
    }
}