package io.jp.security;

import io.jp.services.UserService.UserUnauthorizedException;
import org.springframework.security.core.Authentication;

public class AuthenticationHandler {
    public static void handleAuth(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new UserUnauthorizedException();
        }
    }
}
