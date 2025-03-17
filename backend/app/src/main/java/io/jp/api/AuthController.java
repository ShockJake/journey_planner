package io.jp.api;

import io.jp.database.entities.User;
import io.jp.services.UserService;
import io.jp.services.UserService.UserAlreadyRegisteredException;
import io.jp.services.UserService.UserUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;


import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (UserAlreadyRegisteredException e) {
            return ResponseEntity.status(BAD_REQUEST).body("User is already registered");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            String token = userService.loginUser(user.getUsername(), user.getPassword());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(Map.of("message", "No user with such username"));
        } catch (UserUnauthorizedException e) {
            return ResponseEntity.status(UNAUTHORIZED).body(Map.of("message", "Unauthorized"));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody String username) {
        try {
            userService.deleteUser(username);
            return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(Map.of("message", "No user with such username"));
        }
    }
}
