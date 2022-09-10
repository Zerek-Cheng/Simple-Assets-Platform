package cn.bukkit.sip.api;

import cn.bukkit.sip.orm.ImgsService;
import cn.bukkit.sip.orm.entity.Imgs;
import cn.bukkit.sip.pojo.RestData;
import cn.bukkit.sip.security.CasdoorAuthenticationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
public class UploadController {

    @Value("${spring.servlet.multipart.location}")
    String path;

    @Resource
    ImgsService imgsService;

    @RequestMapping(path = "/upload")
    public RestData upload(@RequestPart(value = "file", required = false) MultipartFile fileReq, CasdoorAuthenticationToken token) {
        Imgs img = imgsService.uploaderImg(fileReq, token.getPrincipal().getId());
        return RestData.builder().data(img).build();
    }
}
