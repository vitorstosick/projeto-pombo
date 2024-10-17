package com.pruu.pombo.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.exception.PruuException;

@Service
public class AuthenticationService {

    private final JwtService jwtService;

    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String authenticate(Authentication authentication) {
        return jwtService.getGeneratedToken(authentication);
    }

    public User getAuthenticatedUser() throws PruuException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = null;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof User) {
                UserDetails userDetails = (User) principal;
                authenticatedUser = (User) userDetails;
            }
        }

        if (authenticatedUser == null) {
            throw new PruuException("User not found");
        }

        return authenticatedUser;
    }
}
