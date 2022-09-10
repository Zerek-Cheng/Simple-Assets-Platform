package cn.bukkit.sip.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestData implements Serializable {
    @Builder.Default
    private int code = 0;
    @Builder.Default
    private String message = "success";
    private Object data = new HashMap<>();
}
