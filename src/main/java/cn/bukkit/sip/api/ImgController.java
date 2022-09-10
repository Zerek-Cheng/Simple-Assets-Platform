package cn.bukkit.sip.api;

import cn.bukkit.sip.exception.RestException;
import cn.bukkit.sip.orm.ImgsService;
import cn.bukkit.sip.orm.entity.Imgs;
import cn.bukkit.sip.pojo.RestData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/img")
@RestController
public class ImgController {

    @Value("${spring.servlet.multipart.location}")
    String path;
    @Autowired
    ImgsService imgsService;

    // 显示图片
    @RequestMapping(path = "/get/{id}", produces = "image/*;charset=utf-8")
    @ResponseBody
    public byte[] img(@PathVariable Long id) {
        byte[] img = imgsService.loadImg(id);
        if (img == null || img.length == 0)
            img = imgsService.loadUnknownImg();
        return img;
    }

    @RequestMapping("/list")
    @ResponseBody
    public RestData imgList(Integer current, Integer size) {
        if (current == null || size == null || current < 1 || size > 20 || size < 1) {
            throw RestException.builder().code(-1).message("参数错误").build();
        }
        Page<Imgs> page = this.imgsService.page(new Page<>(current, size), Wrappers.<Imgs>lambdaQuery().orderByDesc(Imgs::getId));
        return RestData.builder().data(page.getRecords()).build();
    }
}
