package com.stockmarket.sproject.Security.Jwt;

import org.springframework.security.core.GrantedAuthority;

import com.stockmarket.sproject.Security.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Authoritie implements GrantedAuthority {

    UserRoles userRole;

    @Override
    public String getAuthority() {
        return userRole.name();
    }

}
