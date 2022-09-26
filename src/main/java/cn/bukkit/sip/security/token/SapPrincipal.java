package cn.bukkit.sip.security.token;

import org.casbin.casdoor.entity.CasdoorPermission;
import org.casbin.casdoor.entity.CasdoorRole;

import java.util.List;

public interface SapPrincipal {

    String getOwner();

    String getName();

    String getId();

    String getDisplayName();

    String getEmail();

    String getPhone();

    List<CasdoorRole> getRoles();

    List<CasdoorPermission> getPermissions();
}
