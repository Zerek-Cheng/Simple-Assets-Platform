package cn.bukkit.sip.security;

import cn.bukkit.sip.orm.UserService;
import cn.bukkit.sip.orm.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.casbin.casdoor.entity.CasdoorUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${web.url}")
    String webUrl;
    @Resource
    HttpSession session;

    @Resource
    UserService userService;

    @Resource
    ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        if (authentication instanceof CasdoorAuthenticationToken)
            this.writeUserInfo((CasdoorAuthenticationToken) authentication);
        Object callback = session.getAttribute("callback");
        this.getRedirectStrategy().sendRedirect(request, response, callback == null || callback.toString().isBlank() ? webUrl : callback.toString());
        super.onAuthenticationSuccess(request, response, authentication);
    }

    @Async
    void writeUserInfo(CasdoorAuthenticationToken authenticationToken) throws JsonProcessingException {
        CasdoorUser user = authenticationToken.getPrincipal();
        this.userService.saveOrUpdate(User.builder().id(user.getId()).data(user).updateTime(LocalDateTime.now()).build());
        log.debug("{}用户信息更新成功", user.getId());
    }
}
