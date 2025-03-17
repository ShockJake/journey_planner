package io.jp.services;

import io.jp.database.entities.User;
import io.jp.database.repositories.UserRepository;
import io.jp.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyRegisteredException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username %s not found".formatted(username)));
    }

    public String loginUser(String username, String password) {
        var user = findUserByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserUnauthorizedException();
        }
        return jwtUtil.generateToken(username);
    }

    public void deleteUser(String username) {
        var user = findUserByUsername(username);
        //todo: remove all routes connected to user.
        userRepository.delete(user);
    }

    public static class UserAlreadyRegisteredException extends RuntimeException {}
    public static class UserUnauthorizedException extends RuntimeException {}
}
