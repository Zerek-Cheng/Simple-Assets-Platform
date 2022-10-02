package cn.bukkit.sip.controller;

import cn.bukkit.sip.config.SapConfig;
import cn.bukkit.sip.pojo.RestData;
import lombok.SneakyThrows;
import org.casbin.casdoor.service.CasdoorAuthService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping("/login")
@Controller
public class AuthController {

    @Resource
    SapConfig sapConfig;

    @Resource
    private CasdoorAuthService casdoorAuthService;
    @Resource
    HttpSession session;
    @Resource
    HttpServletResponse response;

    @ResponseBody
    @SneakyThrows
    @RequestMapping("/goSignin")
    public RestData goLogin(@RequestParam(required = false) String redirect,
                            String callback,
                            @RequestParam(required = false) boolean go) {
        this.session.setAttribute("callback", callback);
        String url = casdoorAuthService.getSigninUrl(redirect == null || redirect.isBlank() ? this.sapConfig.getCasdoorCallback() : redirect);
        if (go) {
            response.sendRedirect(url);
            return null;
        }
        return RestData.builder().data(url).build();
    }

    @ResponseBody
    @SneakyThrows
    @RequestMapping("/goSignup")
    public RestData goRegister(String redirect,
                               String callback,
                               @RequestParam(required = false) boolean go) {
        this.session.setAttribute("callback", callback);
        String signUpUrl = casdoorAuthService.getSignupUrl(casdoorAuthService.getSigninUrl(redirect));
        if (go) {
            response.sendRedirect(signUpUrl);
            return null;
        }
        return RestData.builder().data(signUpUrl).build();
    }

    @ResponseBody
    @SneakyThrows
    @RequestMapping("/goProfile")
    public RestData goProfile(@RequestParam(required = false) boolean go, @RequestParam(required = false) String returnUrl) {
        String myProfileUrl = this.casdoorAuthService.getMyProfileUrl(null, returnUrl);
        if (go) {
            response.sendRedirect(myProfileUrl);
            return null;
        }
        return RestData.builder().data(myProfileUrl).build();
    }

    @ResponseBody
    @RequestMapping("/logout")
    public RestData logout(HttpSession session) {
        SecurityContextHolder.getContext().setAuthentication(null);
        session.invalidate();
        return RestData.builder().build();
    }

}
