package cn.bukkit.sip.orm.entity;

import cn.bukkit.sip.serializer.LocalDateTimeToTimestampDeserializer;
import cn.bukkit.sip.serializer.LocalDateTimeToTimestampSerializer;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "img", resultMap = "BaseResultMap")
public class ImgEntity implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "path")
    private String path;

    @TableField(value = "size")
    private Integer size;

    @Builder.Default
    @TableField(value = "public")
    private Boolean isPublic = true;

    @TableField(value = "limit_date", updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using = LocalDateTimeToTimestampSerializer.class)
    @JsonDeserialize(using = LocalDateTimeToTimestampDeserializer.class)
    private LocalDateTime dateLimit;

    @TableField(value = "limit_times", updateStrategy = FieldStrategy.IGNORED)
    private Integer timesLimit;

    @TableField(value = "owner")
    private String owner;

    @Builder.Default
    @TableLogic
    @TableField(value = "deleted")
    @JsonIgnore
    private Boolean isDeleted = false;

    @TableField("stronge")
    private String stronge;

    @JsonIgnore
    @TableField(exist = false)
    final private UserEntity userEntity = null;

    public void setDateLimitFromTimestamp(Long dateLimit) {
        this.dateLimit = LocalDateTime.ofEpochSecond(dateLimit, 0, ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now()));
    }

}