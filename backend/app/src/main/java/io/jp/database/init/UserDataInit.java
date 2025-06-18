package io.jp.database.init;

import io.jp.database.entities.user.User;
import io.jp.database.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static io.jp.database.entities.user.UserType.USER;

@Component
@RequiredArgsConstructor
public class UserDataInit implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
       if (userRepository.count() == 0) {
           var user = new User();
           user.setUsername("user");
           user.setPassword(passwordEncoder.encode("1234"));
           user.setUserType(USER);
           user.setRoutesCreated(0);
           userRepository.save(user);
       }
    }
}
