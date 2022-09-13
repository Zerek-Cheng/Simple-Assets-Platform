package cn.bukkit.sip.api;

import cn.bukkit.sip.exception.RestException;
import cn.bukkit.sip.service.ImgService;
import cn.bukkit.sip.orm.UserDaoService;
import cn.bukkit.sip.orm.entity.Img;
import cn.bukkit.sip.pojo.RestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    ImgService imgService;
    @Autowired
    UserDaoService userDaoService;

    @GetMapping("/test")
    public Object test() {
        Img img = imgService.getImgDaoService().getById(1568515003021197313L);
        System.out.println("img = " + img);
        System.out.println("img.getUser() = " + img.getUser());
        System.out.println("img.getUser().getData() = " + img.getUser().getData());
        System.out.println("user = " + img.getUser());
        return null;
    }
}
