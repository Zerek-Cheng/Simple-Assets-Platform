package cn.bukkit.sip.controller;

import cn.bukkit.sip.exception.ImgNotExistException;
import cn.bukkit.sip.exception.RestException;
import cn.bukkit.sip.orm.entity.ImgEntity;
import cn.bukkit.sip.pojo.ImgMetaDto;
import cn.bukkit.sip.pojo.RestData;
import cn.bukkit.sip.security.token.SapToken;
import cn.bukkit.sip.service.AssetsService;
import cn.hutool.core.io.file.FileNameUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/img")
@RestController
@Validated
@Slf4j
public class ImgController {

    @Value("${spring.servlet.multipart.location}")
    String path;
    @Resource
    AssetsService assetsService;


    // 显示图片
    @RequestMapping(path = "/get/{id}")
    @SneakyThrows
    public Object get(@PathVariable Long id, SapToken authentication, HttpServletResponse httpServletResponse) {
        ImgEntity imgEntity = assetsService.getImgDaoService().getById(id);
        Object img = null;
        try {
            if (this.assetsService.permissionCheck(imgEntity, authentication) ||
                    this.assetsService.limitCheck(imgEntity)) {
                this.assetsService.addTimes(imgEntity.getId());
                img = assetsService.loadAsset(id);
            }
        } catch (RestException e) {
            log.debug(e.getMessage());
        } finally {
            if (img == null) img = assetsService.loadUnknownAsset();
        }
        if (img instanceof String) return new RedirectView((String) img);
        if (img instanceof byte[]) {
            httpServletResponse.setContentType(String.format("image/%s;charset=utf-8"
                    , Optional.ofNullable(
                            FileNameUtil.extName(Optional.ofNullable(imgEntity).map(ImgEntity::getPath).orElse(null))).orElse("jpg")));
            httpServletResponse.getOutputStream().write((byte[]) img);
            return null;
        }
        return img;
    }

    @PostMapping(path = "/del/{id}")
    public RestData del(@PathVariable Long id, SapToken authentication) {
        ImgEntity imgEntity = assetsService.getImgDaoService().getById(id);
        if (!this.assetsService.permissionCheck(imgEntity, authentication))
            throw RestException.builder().message("无权操作").build();
        if (!assetsService.getImgDaoService().removeById(id)) throw RestException.builder().message("删除失败").build();
        return RestData.builder().build();
    }

    @RequestMapping(path = "/info/{id}")
    public RestData info(@PathVariable Long id) {
        ImgEntity imgEntity = assetsService.getImgDaoService().getById(id);
        if (imgEntity == null) throw new ImgNotExistException();
        return RestData.builder().data(new HashMap<>() {
            {
                put("info", imgEntity);
                put("times", assetsService.getTimes(id));
            }
        }).build();
    }

    @PostMapping(path = "/edit/{id}")
    public RestData edit(@PathVariable Long id, ImgMetaDto dto, SapToken authenticationToken) {
        ImgEntity imgEntity = assetsService.getImgDaoService().getById(id);
        if (!this.assetsService.permissionCheck(imgEntity, authenticationToken))
            throw RestException.builder().message("无权操作").build();

        if (dto.getIsPublic() != null) imgEntity.setIsPublic(dto.getIsPublic());
        if (dto.getDateLimit() != null)
            if (dto.getDateLimit() != 0)
                imgEntity.setDateLimitFromTimestamp(dto.getDateLimit() / 1000);
            else
                imgEntity.setDateLimit(null);
        if (dto.getTimesLimit() != null) imgEntity.setTimesLimit(dto.getTimesLimit());
        this.assetsService.getImgDaoService().updateById(imgEntity);
        return RestData.builder().build();
    }


    @PostMapping("/list")
    public RestData list(@NotNull @DecimalMin("1") Integer current, @NotNull @DecimalMax("100") Integer size,
                         @RequestParam(required = false, defaultValue = "false") boolean self,
                         @RequestParam(required = false) String search, SapToken token) {
        if (self && token == null) throw RestException.builder().message("未登录").build();
        Page<ImgEntity> page = self ?
                this.assetsService.getUserPage(current, size, token.getPrincipal().getId(),
                        "%" + Optional.ofNullable(search).orElse("").replace(" ", "%") + "%") :
                this.assetsService.getPublicPage(current, size);
        return RestData.builder().data(new HashMap<>() {
            {
                put("img", self ? page.getRecords() : page.getRecords().stream().filter(r -> assetsService.limitCheck(r)).collect(Collectors.toList()));
                put("hasNext", page.getSize() >= size);
                put("total", page.getTotal());
            }
        }).build();
    }

    @RequestMapping("/total")
    public RestData userImgTotal(SapToken token) {
        return RestData.builder().data(this.assetsService.userTotal(token.getPrincipal().getId())).build();
    }
}
