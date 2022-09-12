package cn.bukkit.sip.orm.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "img", resultMap = "BaseResultMap")
public class Img implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "path")
    private String path;

    @TableField(value = "size")
    private Integer size;

    @TableField(value = "owner")
    private String owner;

    @Builder.Default
    @TableLogic
    @TableField(value = "deleted")
    private Boolean deleted = false;

    @JsonIgnore
    @TableField(exist = false)
    final private User user = null;
}