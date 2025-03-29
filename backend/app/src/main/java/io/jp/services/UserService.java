package io.jp.services;

import io.jp.database.entities.User;
import io.jp.database.entities.UserType;
import io.jp.database.repositories.UserRepository;
import io.jp.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static io.jp.database.entities.UserType.USER;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyRegisteredException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserType(USER);
        user.setRoutesCreated(0);
        userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username %s not found".formatted(username)));
    }

    public String loginUser(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return jwtUtil.generateToken(username);
    }

    public void deleteUser(String username) {
        var user = findUserByUsername(username);
        //todo: remove all routes connected to user.
        userRepository.delete(user);
    }

    public void changeUsername(String username, String newUsername) {
        var user = findUserByUsername(username);
        user.setUsername(newUsername);
        userRepository.save(user);
    }

    public void changePassword(String username, String newPassword) {
        var user = findUserByUsername(username);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public static class UserAlreadyRegisteredException extends RuntimeException {}
    public static class UserUnauthorizedException extends RuntimeException {}
}
