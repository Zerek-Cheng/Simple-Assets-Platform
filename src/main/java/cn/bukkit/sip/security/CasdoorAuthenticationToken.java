package cn.bukkit.sip.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.casbin.casdoor.entity.CasdoorUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CasdoorAuthenticationToken implements Authentication {
    String code;
    String state;
    Object Credentials;
    Object Details;
    CasdoorUser Principal;
    boolean Authenticated;
    String name;
    Collection<GrantedAuthority> Authorities = new ArrayList<>();

}
