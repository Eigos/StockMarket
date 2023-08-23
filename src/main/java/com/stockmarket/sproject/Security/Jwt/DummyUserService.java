package com.stockmarket.sproject.Security.Jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.stockmarket.sproject.Security.UserRoles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

@Service
public class DummyUserService {

    private Map<String, User> users = new HashMap<>();

    @PostConstruct
    public void initialize() {
        users.put("user",
                new User("user",
                        "user123",
                        new ArrayList<GrantedAuthority>(List.of(
                                new Authoritie(UserRoles.ROLE_USER)))));

        users.put("admin",
                new User("admin",
                        "admin123",
                        new ArrayList<Authoritie>(
                                List.of(
                                        new Authoritie(UserRoles.ROLE_USER),
                                        new Authoritie(UserRoles.ROLE_ADMIN)))));

    }

    public User getUserByUsername(String username) {
        return users.get(username);
    }
}
