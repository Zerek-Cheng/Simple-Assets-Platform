package cn.bukkit.sip.api;

import cn.bukkit.sip.pojo.UploadImgDTO;
import cn.bukkit.sip.service.ImgService;
import cn.bukkit.sip.orm.UserDaoService;
import cn.bukkit.sip.orm.entity.ImgEntity;
import cn.bukkit.sip.pojo.RestData;
import cn.bukkit.sip.security.CasdoorAuthenticationToken;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Optional;

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
    public RestData upload(@RequestPart(value = "file") MultipartFile fileReq,
                           UploadImgDTO uploadImgDTO,
                           @NotNull CasdoorAuthenticationToken token) {
        ImgEntity imgEntity = imgService.uploaderImg(fileReq, userDaoService.getById(token.getPrincipal().getId()), uploadImgDTO);
        if (!uploadImgDTO.getIsPublic()) {
            imgEntity.setIsPublic(false);
            this.imgService.getImgDaoService().updateById(imgEntity);
        }
        return RestData.builder().data(imgEntity).build();
    }
}
