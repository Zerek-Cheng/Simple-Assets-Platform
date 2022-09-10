package cn.bukkit.sip.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${web.url}")
    String webUrl;
    @Resource
    HttpSession session;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        this.clearAuthenticationAttributes(request);
        Object callback = session.getAttribute("callback");
        this.getRedirectStrategy().sendRedirect(request, response, callback == null || callback.toString().isBlank() ? webUrl : callback.toString());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
