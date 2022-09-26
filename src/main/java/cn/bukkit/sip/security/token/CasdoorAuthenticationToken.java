package cn.bukkit.sip.security.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CasdoorAuthenticationToken implements SapToken {
    String code;
    String state;
    Object Credentials;
    Object Details;
    CasdoorSapPrincipal Principal;
    boolean Authenticated;
    Collection<GrantedAuthority> Authorities = new ArrayList<>();

    @Override
    public String getName() {
        return this.getPrincipal().getName();
    }
}
