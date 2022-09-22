package cn.bukkit.sip.orm.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.casbin.casdoor.entity.CasdoorUser;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user", resultMap = "BaseResultMap")
public class UserEntity implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @TableField(value = "data", typeHandler = cn.bukkit.sip.orm.handler.CasdoorTypeHandler.class)
    private CasdoorUser data;

    @Builder.Default
    @Version
    @TableField(value = "updateTime")
    private LocalDateTime updateTime = LocalDateTime.now();

}