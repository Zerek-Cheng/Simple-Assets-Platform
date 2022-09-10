package cn.bukkit.sip.exception;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WebException extends RuntimeException {
    String message;
    String redirect;
}
