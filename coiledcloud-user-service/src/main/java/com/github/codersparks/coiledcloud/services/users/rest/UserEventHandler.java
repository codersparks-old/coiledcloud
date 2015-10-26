package com.github.codersparks.coiledcloud.services.users.rest;

import com.github.codersparks.coiledcloud.services.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by codersparks on 12/10/2015.
 */
@Component
@RepositoryEventHandler(User.class)
public class UserEventHandler {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserEventHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @HandleBeforeCreate
    public void handleUserCreate(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
