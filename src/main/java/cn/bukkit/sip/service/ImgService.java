package cn.bukkit.sip.service;

import cn.bukkit.sip.config.SapConfig;
import cn.bukkit.sip.exception.ImgNotExistException;
import cn.bukkit.sip.exception.RestException;
import cn.bukkit.sip.exception.UploadException;
import cn.bukkit.sip.orm.ImgDaoService;
import cn.bukkit.sip.orm.entity.ImgEntity;
import cn.bukkit.sip.orm.entity.UserEntity;
import cn.bukkit.sip.pojo.ImgMetaDto;
import cn.bukkit.sip.security.token.SapToken;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@Data
@Slf4j
public class ImgService {

    @Resource
    SapConfig sapConfig;

    @Resource
    StrongeService strongeService;

    @Resource
    ImgDaoService imgDaoService;

    @Resource
    RedisTemplate<String, Integer> redisTemplate;

    public static final String KEY_TIMES = "img-times::";

    /**
     * 上传图片
     *
     * @param fileUpload 文件
     * @param userEntity 用户信息
     * @param imgMetaDto 图片信息
     * @return 图片信息
     */
    public ImgEntity uploaderImg(MultipartFile fileUpload, UserEntity userEntity, ImgMetaDto imgMetaDto) throws RestException, IOException {
        String fileType = Objects.requireNonNull(fileUpload.getOriginalFilename()).substring(fileUpload.getOriginalFilename().lastIndexOf("."));
        if (this.sapConfig.getAllowUpload().stream().map((x) -> "." + x).noneMatch(fileType::equalsIgnoreCase))
            throw RestException.builder().message("不允许上传的文件类型").build();
        String fileName = new Date().getTime() + "-" + DigestUtil.md5Hex(fileUpload.getOriginalFilename()) + fileType;
        if (this.strongeService.getDefault().isExist(fileName)) throw new UploadException("文件已存在");
        this.strongeService.getDefault().saveFile(fileName, fileUpload.getBytes());
        log.debug("上传文件:{}-{}-{}-{}", this.strongeService.getDefault().getName(), userEntity.getId(), fileName, fileUpload.getSize());
        ImgEntity imgEntity = ImgEntity.builder().name(fileUpload.getOriginalFilename()).path(fileName).size((int) fileUpload.getSize()).timesLimit(Optional.ofNullable(imgMetaDto.getTimesLimit()).orElse(0)).owner(Optional.ofNullable(userEntity).map(UserEntity::getId).orElse("")).build();
        imgEntity.setDateLimitFromTimestamp(Optional.ofNullable(imgMetaDto.getDateLimit()).orElse(253402271999000L) / 1000);
        this.imgDaoService.saveOrUpdate(imgEntity);
        return imgEntity;
    }

    /**
     * 获取图片
     *
     * @param id 图片id
     * @return 图片
     */
    public byte[] loadImg(Long id) {
        ImgEntity imgEntity = this.imgDaoService.getById(id);
        if (imgEntity == null) throw new ImgNotExistException();
        return Optional.ofNullable(this.strongeService.getDefault().getFile(imgEntity.getPath())).orElseThrow(ImgNotExistException::new);
    }

    /**
     * 获取未知图片
     *
     * @return 图片
     */
    public byte[] loadUnknownImg() {
        return FileUtil.readBytes(new ClassPathResource("unknown.png").getFile());
    }

    /**
     * 检查是否拥有编辑权
     *
     * @param imgEntity      图片
     * @param authentication 用户信息
     * @return 是否拥有编辑权
     */
    public boolean permissionCheck(ImgEntity imgEntity, SapToken authentication) {
        if (imgEntity == null) throw new ImgNotExistException();
        return !imgEntity.getOwner().isBlank() && (authentication != null &&
                (imgEntity.getOwner().equalsIgnoreCase(authentication.getPrincipal().getId()) ||
                        authentication.getPrincipal().getRoles().stream().anyMatch(role -> role.getName().contains("admin")))
        );
    }

    /**
     * 查询是否可以展示
     *
     * @param imgEntity 图片
     * @return true: 未达到限制标准
     */
    public boolean limitCheck(ImgEntity imgEntity) {
        if (imgEntity == null) throw new ImgNotExistException();
        return !(imgEntity.getDateLimit() != null && imgEntity.getDateLimit().isBefore(LocalDateTime.now()) || (Optional.ofNullable(imgEntity.getTimesLimit()).orElse(0) > 0 && this.getTimes(imgEntity.getId()) >= imgEntity.getTimesLimit()));
    }

    /**
     * 获取已展示次数
     *
     * @param id 图片id
     * @return 已展示次数
     */
    public int getTimes(Long id) {
        return Optional.ofNullable(this.redisTemplate.opsForValue().get(KEY_TIMES + id)).orElse(0);
    }

    /**
     * 增加展示次数
     *
     * @param id 图片id
     */
    @Async
    public void addTimes(Long id) {
        this.redisTemplate.opsForValue().increment(KEY_TIMES + id);
    }

    /**
     * 获取公共列表
     *
     * @param page 页码
     * @param size 每页数量
     * @return 公共列表
     */
    public Page<ImgEntity> getPage(int page, int size) {
        return this.getPage(page, size, null, Optional.of(true), true, null);
    }

    /**
     * 获取单个用户图片列表
     *
     * @param page    页码
     * @param size    每页数量
     * @param ownerId 用户id,如果为null则获取公共图片
     * @return 单个用户图片列表
     */
    public Page<ImgEntity> getPage(int page, int size, String ownerId) {
        return this.getPage(page, size, ownerId,
                ownerId == null ? Optional.of(true) : Optional.empty(), false, null);
    }

    public Page<ImgEntity> getPage(int page, int size, String ownerId, String nameLike) {
        return this.getPage(page, size, ownerId,
                ownerId == null ? Optional.of(true) : Optional.empty(), false, nameLike);
    }

    /**
     * 获取图片列表
     *
     * @param page           页数从1开始
     * @param size           页数大小
     * @param ownerId        用户id
     * @param isPublic       是否公共,为null则不检查
     * @param checkDateLimit 是否检查时间限制
     * @return 图片列表
     */
    public Page<ImgEntity> getPage(int page, int size, String ownerId, Optional<Boolean> isPublic, boolean checkDateLimit, String nameLike) {
        LambdaQueryWrapper<ImgEntity> wrapper = Wrappers.lambdaQuery();
        if (isPublic.isPresent()) wrapper.eq(ImgEntity::getIsPublic, isPublic.orElse(true));
        if (ownerId != null && !ownerId.isBlank()) wrapper.eq(ImgEntity::getOwner, ownerId);
        if (checkDateLimit)
            wrapper.and(i -> i.isNull(ImgEntity::getDateLimit).or().gt(ImgEntity::getDateLimit, LocalDateTime.now()));
        if (nameLike != null && !nameLike.isBlank()) wrapper.like(ImgEntity::getName, nameLike);
        wrapper.orderByDesc(ImgEntity::getId);
        return this.getImgDaoService().page(new Page<>(page, size), wrapper);
    }

    /**
     * 获取用户图片总数
     *
     * @param ownerId 用户id
     * @return 用户图片总数
     */
    @Cacheable(cacheNames = ImgDaoService.KEY_USER_TOTAL, key = "#ownerId")
    public int userTotal(String ownerId) {
        return (int) this.getImgDaoService().count(Wrappers.lambdaQuery(ImgEntity.class).eq(ImgEntity::getOwner, ownerId));
    }
}
