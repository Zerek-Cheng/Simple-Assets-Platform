package cn.bukkit.sip.controller;

import cn.bukkit.sip.exception.RestException;
import cn.bukkit.sip.pojo.RestData;
import cn.bukkit.sip.service.ImgService;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.HashMap;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/csrf")
    public RestData test(CsrfToken token) {
        return RestData.builder().data(new HashMap<>() {
            {
                put("token", token.getToken());
            }
        }).build();
    }

    @GetMapping("/user")
    public Object user(Principal principal) {
        RestData.RestDataBuilder builder = RestData.builder();
        if (principal == null) {
            throw RestException.builder().code(-1).message("未登录").build();
        } else {
            builder.data(principal);
        }
        return builder.build();
    }

    @Resource
    ImgService imgService;

    @GetMapping("/test")
    public Object test() {
        return null;
    }
}
