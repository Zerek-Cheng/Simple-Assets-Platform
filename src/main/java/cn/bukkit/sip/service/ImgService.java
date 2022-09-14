package cn.bukkit.sip.service;

import cn.bukkit.sip.exception.RestException;
import cn.bukkit.sip.orm.ImgDaoService;
import cn.bukkit.sip.orm.entity.Img;
import cn.bukkit.sip.orm.entity.User;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Data
@ConfigurationProperties(prefix = "web.upload")
public class ImgService {
    @Value("${spring.servlet.multipart.location}")
    String path;

    List<String> allowUpload;

    @Resource
    ImgDaoService imgDaoService;

    public Img uploaderImg(MultipartFile fileUpload, User user) throws RestException, IOException {
        String fileType = Objects.requireNonNull(fileUpload.getOriginalFilename()).substring(fileUpload.getOriginalFilename().lastIndexOf("."));
        if (this.allowUpload.stream().map((x) -> "." + x).noneMatch(fileType::equalsIgnoreCase))
            throw RestException.builder().message("不允许上传的文件类型").build();
        String fileName = new Date().getTime() + "-" + DigestUtil.md5Hex(fileUpload.getOriginalFilename()) + fileType;
        String filePath = path + fileName;
        File file = new File(filePath);
        if (file.exists()) file.delete();
        fileUpload.transferTo(file);
        Img img = Img.builder().name(fileUpload.getOriginalFilename()).path(fileName).size((int) fileUpload.getSize()).owner(user.getId()).build();
        this.imgDaoService.saveOrUpdate(img);
        return img;
    }

    public byte[] loadImg(Long id) {
        Img img = this.imgDaoService.getById(id);
        if (img == null) {
            return null;
        }
        File imgFile = new File(this.path + img.getPath());
        return imgFile.exists() ? FileUtil.readBytes(imgFile) : null;
    }

    public byte[] loadUnknownImg() {
        return FileUtil.readBytes(new ClassPathResource("unknown.png").getFile());
    }
}
