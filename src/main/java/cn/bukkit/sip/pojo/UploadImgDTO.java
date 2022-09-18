package cn.bukkit.sip.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadImgDTO {
    @Builder.Default
    private Boolean isPublic = true;
    @Digits(message = "日期必须有效", fraction = 0, integer = 10)
    private Long dateLimit = LocalDateTime.parse("2038-01-01T00:00:00").toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now()));
    @Size(min = 0, max = 10000)
    @Builder.Default
    private Integer timesLimit = 0;
}
