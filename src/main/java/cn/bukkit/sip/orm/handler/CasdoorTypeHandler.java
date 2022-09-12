package cn.bukkit.sip.orm.handler;

import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.casbin.casdoor.entity.CasdoorUser;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CasdoorTypeHandler extends AbstractJsonTypeHandler<CasdoorUser> {
    @Resource
    ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    protected CasdoorUser parse(String json) {
        return this.objectMapper.readValue(json, CasdoorUser.class);
    }

    @SneakyThrows
    @Override
    protected String toJson(CasdoorUser obj) {
        return this.objectMapper.writeValueAsString(obj);
    }
}
