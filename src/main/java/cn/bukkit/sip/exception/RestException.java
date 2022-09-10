package cn.bukkit.sip.exception;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RestException extends RuntimeException {
    @Builder.Default
    Integer code = -1;
    @Builder.Default
    String message = "error";
    Object data;
}
