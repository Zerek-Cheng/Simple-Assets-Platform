package cn.bukkit.sip.security.token;

import org.springframework.security.core.Authentication;

public interface SapToken extends Authentication {
    CasdoorSapPrincipal getPrincipal();

}
