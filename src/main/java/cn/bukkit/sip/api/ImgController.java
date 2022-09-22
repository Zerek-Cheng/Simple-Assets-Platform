package cn.bukkit.sip.api;

import cn.bukkit.sip.exception.RestException;
import cn.bukkit.sip.orm.entity.ImgEntity;
import cn.bukkit.sip.pojo.RestData;
import cn.bukkit.sip.pojo.ImgMetaDto;
import cn.bukkit.sip.security.CasdoorAuthenticationToken;
import cn.bukkit.sip.service.ImgService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/img")
@RestController
@Validated
public class ImgController {

    @Value("${spring.servlet.multipart.location}")
    String path;
    @Resource
    ImgService imgService;

    // 显示图片
    @RequestMapping(path = "/get/{id}", produces = "image/*;charset=utf-8")
    public byte[] get(@PathVariable Long id, CasdoorAuthenticationToken authentication) {
        ImgEntity imgEntity = imgService.getImgDaoService().getById(id);
        byte[] img = null;
        if (imgEntity != null &&
                ((authentication != null && this.imgService.checkPermission(imgEntity, authentication)) ||
                        this.imgService.limitCheck(imgEntity))) {
            this.imgService.addTimes(imgEntity.getId());
            img = imgService.loadImg(id);
        }
        if (img == null || img.length == 0) img = imgService.loadUnknownImg();
        return img;
    }

    @PostMapping(path = "/del/{id}")
    public RestData del(@PathVariable Long id, CasdoorAuthenticationToken authentication) {
        ImgEntity imgEntity = imgService.getImgDaoService().getById(id);
        if (imgEntity == null) throw RestException.builder().message("图片不存在").build();
        if (!this.imgService.checkPermission(imgEntity, authentication))
            throw RestException.builder().message("无权操作").build();
        if (!imgService.getImgDaoService().removeById(id)) throw RestException.builder().message("删除失败").build();
        return RestData.builder().build();
    }

    @RequestMapping(path = "/info/{id}")
    public RestData info(@PathVariable Long id) {
        ImgEntity imgEntity = imgService.getImgDaoService().getById(id);
        if (imgEntity == null) throw RestException.builder().message("图片不存在").build();
        return RestData.builder().data(new HashMap<>() {
            {
                put("info", imgEntity);
                put("times", imgService.getTimes(id));
            }
        }).build();
    }

    @PostMapping(path = "/edit/{id}")
    public RestData edit(@PathVariable Long id, ImgMetaDto dto, CasdoorAuthenticationToken authenticationToken) {
        ImgEntity imgEntity = imgService.getImgDaoService().getById(id);
        if (imgEntity == null) throw RestException.builder().message("图片不存在").build();
        if (!this.imgService.checkPermission(imgEntity, authenticationToken))
            throw RestException.builder().message("无权操作").build();
        if (dto.getIsPublic() != null) imgEntity.setIsPublic(dto.getIsPublic());
        if (dto.getDateLimit() != null && dto.getDateLimit() != 0)
            imgEntity.setDateLimitFromTimestamp(dto.getDateLimit() / 1000);
        else if (Optional.ofNullable(dto.getDateLimit()).orElse(0L) == 0L)
            imgEntity.setDateLimit(null);
        if (dto.getTimesLimit() != null) imgEntity.setTimesLimit(dto.getTimesLimit());
        this.imgService.getImgDaoService().updateById(imgEntity);
        return RestData.builder().build();
    }


    @PostMapping("/list")
    public RestData list(@NotNull @DecimalMin("1") Integer current, @NotNull @DecimalMax("20") Integer size) {
        Page<ImgEntity> page = this.imgService.getImgDaoService().page(
                new Page<>(current, size),
                Wrappers.<ImgEntity>lambdaQuery()
                        .eq(ImgEntity::getIsPublic, true)
                        .and(i -> i.isNull(ImgEntity::getDateLimit).or().gt(ImgEntity::getDateLimit, LocalDateTime.now()))
                        .orderByDesc(ImgEntity::getId));
        return RestData.builder().data(new HashMap<>() {
            {
                put("img", page.getRecords().stream().filter(r -> imgService.limitCheck(r)).collect(Collectors.toList()));
                put("hasNext", page.getSize() >= size);
            }
        }).build();
    }
}
