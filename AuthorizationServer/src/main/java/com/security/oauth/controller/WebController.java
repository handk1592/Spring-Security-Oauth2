package com.security.oauth.controller;

import com.security.oauth.service.UserService;
import com.security.oauth.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    @Autowired
    UserService userService;

    @GetMapping("/hello")
    public User hello() {
        return userService.findByName("handg");
    }
}
