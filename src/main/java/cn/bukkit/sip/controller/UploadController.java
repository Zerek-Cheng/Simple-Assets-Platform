package cn.bukkit.sip.controller;

import cn.bukkit.sip.orm.UserDaoService;
import cn.bukkit.sip.orm.entity.ImgEntity;
import cn.bukkit.sip.pojo.ImgMetaDto;
import cn.bukkit.sip.pojo.RestData;
import cn.bukkit.sip.security.token.SapToken;
import cn.bukkit.sip.service.AssetsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

@RestController
public class UploadController {

    @Value("${spring.servlet.multipart.location}")
    String path;

    @Resource
    AssetsService assetsService;

    @Resource
    UserDaoService userDaoService;

    @SneakyThrows
    @RequestMapping(path = "/upload")
    public RestData upload(@RequestPart(value = "file") MultipartFile fileReq,
                           ImgMetaDto imgMetaDto,
                           SapToken token) {
        ImgEntity imgEntity = assetsService.uploadAsset(fileReq, userDaoService.getById(token.getPrincipal().getId()), imgMetaDto);
        imgEntity.setIsPublic(Optional.ofNullable(imgMetaDto.getIsPublic()).orElse(true));
        this.assetsService.getImgDaoService().updateById(imgEntity);
        return RestData.builder().data(imgEntity).build();
    }

    @RequestMapping(path = "/storage/list")
    public RestData getStorageList() {
        return RestData.builder().data(
                new HashMap<>() {
                    {
                        put("list", assetsService.getStorageService().getStorageMap().entrySet().stream().map(s -> {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("value", s.getKey());
                            map.put("label", s.getValue().getName());
                            return map;
                        }).toList());
                        put("default", assetsService.getSapConfig().getStorageType());
                    }
                }
        ).build();
    }
}
