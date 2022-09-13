package cn.bukkit.sip.api;

import cn.bukkit.sip.service.ImgService;
import cn.bukkit.sip.orm.UserDaoService;
import cn.bukkit.sip.orm.entity.Img;
import cn.bukkit.sip.pojo.RestData;
import cn.bukkit.sip.security.CasdoorAuthenticationToken;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@RestController
public class UploadController {

    @Value("${spring.servlet.multipart.location}")
    String path;

    @Resource
    ImgService imgService;

    @Resource
    UserDaoService userDaoService;

    @SneakyThrows
    @RequestMapping(path = "/upload")
    public RestData upload(@RequestPart(value = "file") MultipartFile fileReq, @NotNull CasdoorAuthenticationToken token) {
        Img img = imgService.uploaderImg(fileReq, userDaoService.getById(token.getPrincipal().getId()));
        return RestData.builder().data(img).build();
    }
}
