package cn.bukkit.sip.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImgMetaDto {
    private Boolean isPublic;
    @Digits(message = "日期必须有效", fraction = 0, integer = 13)
    private Long dateLimit;
    @Size(min = 0, max = 10000)
    private Integer timesLimit;

    private String storage;
}
