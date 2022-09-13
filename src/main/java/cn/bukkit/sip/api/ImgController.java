package cn.bukkit.sip.api;

import cn.bukkit.sip.exception.RestException;
import cn.bukkit.sip.orm.entity.Img;
import cn.bukkit.sip.pojo.RestData;
import cn.bukkit.sip.security.CasdoorAuthenticationToken;
import cn.bukkit.sip.service.ImgService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

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
    @ResponseBody
    public byte[] get(@PathVariable Long id) {
        byte[] img = imgService.loadImg(id);
        if (img == null || img.length == 0) img = imgService.loadUnknownImg();
        return img;
    }


    @RequestMapping(path = "/del/{id}")
    @ResponseBody
    public RestData del(@PathVariable Long id, CasdoorAuthenticationToken authentication) {
        Img img = imgService.getImgDaoService().getById(id);
        if (img == null) throw RestException.builder().message("图片不存在").build();
        if (img.getOwner().isBlank()
                ||
                !(authentication != null &&
                        (authentication.getPrincipal().getRoles().stream().anyMatch(role -> role.getName().contains("admin"))
                                || img.getOwner().equalsIgnoreCase(authentication.getPrincipal().getId()))
                )
        )
            throw RestException.builder().message("无权操作").build();
        if (!imgService.getImgDaoService().removeById(id)) throw RestException.builder().message("删除失败").build();
        return RestData.builder().build();
    }

    @RequestMapping(path = "/info/{id}")
    @ResponseBody
    public RestData info(@PathVariable Long id) {
        Img img = imgService.getImgDaoService().getById(id);
        if (img == null) throw RestException.builder().message("图片不存在").build();
        return RestData.builder().data(img).build();
    }


    @RequestMapping("/list")
    @ResponseBody
    public RestData list(@NotNull @DecimalMin("1") Integer current, @NotNull @DecimalMax("20") Integer size) {
        Page<Img> page = this.imgService.getImgDaoService().page(new Page<>(current, size), Wrappers.<Img>lambdaQuery().orderByDesc(Img::getId));
        return RestData.builder().data(page.getRecords()).build();
    }
}
