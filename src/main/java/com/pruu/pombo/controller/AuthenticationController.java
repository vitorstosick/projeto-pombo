package com.pruu.pombo.controller;

import com.pruu.pombo.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    @PostMapping("authenticate")
    public String authenticate(Authentication authentication) {
        return service.authenticate(authentication);
    }
}
