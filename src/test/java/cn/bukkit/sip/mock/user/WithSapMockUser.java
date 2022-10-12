package cn.bukkit.sip.mock.user;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = SapMockUserContextFactory.class)
public @interface WithSapMockUser {
}
