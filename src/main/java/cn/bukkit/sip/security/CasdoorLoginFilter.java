package cn.bukkit.sip.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class CasdoorLoginFilter extends AbstractAuthenticationProcessingFilter {

    public CasdoorLoginFilter(@Autowired AuthenicationManagerImpl authenicationManager, @Autowired LoginSuccessHandler loginSuccessHandler) {
        super(new AntPathRequestMatcher("/login/callback", "GET"), authenicationManager);
        this.setAuthenticationSuccessHandler(loginSuccessHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("CasdoorLoginFilter attemptAuthentication");
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        if (code == null || state == null || code.isEmpty() || state.isEmpty()) {
            throw new AuthenticationServiceException("code or state is null");
        }
        CasdoorAuthenticationToken token = CasdoorAuthenticationToken.builder().code(code).state(state).build();
        token.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(token);
    }
}
