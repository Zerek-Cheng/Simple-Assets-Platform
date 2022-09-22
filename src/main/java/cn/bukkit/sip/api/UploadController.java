package cn.bukkit.sip.api;

import cn.bukkit.sip.orm.UserDaoService;
import cn.bukkit.sip.orm.entity.ImgEntity;
import cn.bukkit.sip.pojo.RestData;
import cn.bukkit.sip.pojo.ImgMetaDto;
import cn.bukkit.sip.security.CasdoorAuthenticationToken;
import cn.bukkit.sip.service.ImgService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@Validated
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
                           ImgMetaDto imgMetaDto,
                           @NotNull CasdoorAuthenticationToken token) {
        ImgEntity imgEntity = imgService.uploaderImg(fileReq, userDaoService.getById(token.getPrincipal().getId()), imgMetaDto);
        imgEntity.setIsPublic(Optional.ofNullable(imgMetaDto.getIsPublic()).orElse(true));
        this.imgService.getImgDaoService().updateById(imgEntity);
        return RestData.builder().data(imgEntity).build();
    }
}
