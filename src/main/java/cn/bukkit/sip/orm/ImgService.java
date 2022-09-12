package cn.bukkit.sip.orm;

import cn.bukkit.sip.orm.entity.Img;
import cn.bukkit.sip.orm.entity.User;
import cn.bukkit.sip.orm.mapper.ImgMapper;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.Objects;

@Service
public class ImgService extends ServiceImpl<ImgMapper, Img> {
    @Value("${spring.servlet.multipart.location}")
    String path;

    @SneakyThrows
    public Img uploaderImg(MultipartFile fileUpload, User user) {
        String fileType = Objects.requireNonNull(fileUpload.getOriginalFilename()).substring(fileUpload.getOriginalFilename().lastIndexOf("."));
        String fileName = new Date().getTime() + "-" + DigestUtil.md5Hex(fileUpload.getOriginalFilename()) + fileType;
        String filePath = path + fileName;
        File file = new File(filePath);
        if (file.exists()) file.delete();
        fileUpload.transferTo(file);
        Img img = Img.builder().name(fileUpload.getOriginalFilename()).path(fileName).size((int) fileUpload.getSize()).owner(user.getId()).build();
        this.saveOrUpdate(img);
        return img;
    }

    public byte[] loadImg(Long id) {
        Img img = this.getById(id);
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
