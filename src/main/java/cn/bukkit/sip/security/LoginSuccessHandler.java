package cn.bukkit.sip.security;

import cn.bukkit.sip.orm.UserDaoService;
import cn.bukkit.sip.orm.entity.UserEntity;
import cn.bukkit.sip.security.token.CasdoorAuthenticationToken;
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
import java.util.Optional;

@Slf4j
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${web.url}")
    String webUrl;
    @Resource
    HttpSession session;

    @Resource
    UserDaoService userDaoService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        if (authentication instanceof CasdoorAuthenticationToken)
            this.writeUserInfo((CasdoorAuthenticationToken) authentication);
        this.getRedirectStrategy().sendRedirect(request, response, Optional.ofNullable(session.getAttribute("callback")).orElse(webUrl).toString());
        super.onAuthenticationSuccess(request, response, authentication);
    }

    @Async
    void writeUserInfo(CasdoorAuthenticationToken authenticationToken) {
        CasdoorUser user = authenticationToken.getPrincipal();
        this.userDaoService.saveOrUpdate(UserEntity.builder().id(user.getId()).data(user).updateTime(LocalDateTime.now()).build());
        log.info("{}用户信息更新成功", user.getId());
    }
}
