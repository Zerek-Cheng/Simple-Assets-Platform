package cn.bukkit.sip.service;

import cn.bukkit.sip.exception.RestException;
import cn.bukkit.sip.orm.ImgDaoService;
import cn.bukkit.sip.orm.entity.ImgEntity;
import cn.bukkit.sip.orm.entity.UserEntity;
import cn.bukkit.sip.pojo.UploadImgDTO;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Data
@ConfigurationProperties(prefix = "web.upload")
public class ImgService {
    @Value("${spring.servlet.multipart.location}")
    String path;

    List<String> allowUpload;

    @Resource
    ImgDaoService imgDaoService;

    @Resource
    RedisTemplate<String, Integer> redisTemplate;

    public static final String KEY_TIMES = "img-times::";

    public ImgEntity uploaderImg(MultipartFile fileUpload, UserEntity userEntity, UploadImgDTO uploadImgDTO) throws RestException, IOException {
        String fileType = Objects.requireNonNull(fileUpload.getOriginalFilename()).substring(fileUpload.getOriginalFilename().lastIndexOf("."));
        if (this.allowUpload.stream().map((x) -> "." + x).noneMatch(fileType::equalsIgnoreCase))
            throw RestException.builder().message("不允许上传的文件类型").build();
        String fileName = new Date().getTime() + "-" + DigestUtil.md5Hex(fileUpload.getOriginalFilename()) + fileType;
        String filePath = path + fileName;
        File file = new File(filePath);
        if (file.exists()) throw RestException.builder().message("文件已存在").build();
        fileUpload.transferTo(file);
        ImgEntity imgEntity = ImgEntity.builder()
                .name(fileUpload.getOriginalFilename())
                .path(fileName)
                .size((int) fileUpload.getSize())
                .timesLimit(uploadImgDTO.getTimesLimit())
                .owner(Optional.ofNullable(userEntity).map(UserEntity::getId).orElse(""))
                .build();
        imgEntity.setDateLimitFromTimestamp(uploadImgDTO.getDateLimit());
        this.imgDaoService.saveOrUpdate(imgEntity);
        return imgEntity;
    }

    public byte[] loadImg(Long id) {
        ImgEntity imgEntity = this.imgDaoService.getById(id);
        if (imgEntity == null) {
            return null;
        }
        File imgFile = new File(this.path + imgEntity.getPath());
        return imgFile.exists() ? FileUtil.readBytes(imgFile) : null;
    }

    public byte[] loadUnknownImg() {
        return FileUtil.readBytes(new ClassPathResource("unknown.png").getFile());
    }

    public int getTimes(Long id) {
        return Optional.ofNullable(this.redisTemplate.opsForValue().get(KEY_TIMES + id)).orElse(0);
    }

    @Async
    public void addTimes(Long id) {
        this.redisTemplate.opsForValue().increment(KEY_TIMES + id);
    }
}
