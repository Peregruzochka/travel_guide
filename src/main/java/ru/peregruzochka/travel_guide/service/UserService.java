package ru.peregruzochka.travel_guide.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.peregruzochka.travel_guide.model.User;
import ru.peregruzochka.travel_guide.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User createUser(String name) {
        User user = new User();
        user.setUsername(name);
        User savedUser = userRepository.save(user);
        log.info("User created: {}", savedUser);
        return savedUser;
    }
}
