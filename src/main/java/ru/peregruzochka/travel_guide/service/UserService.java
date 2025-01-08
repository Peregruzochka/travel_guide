package ru.peregruzochka.travel_guide.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.peregruzochka.travel_guide.model.User;
import ru.peregruzochka.travel_guide.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User createUser(String name) {
        User user = new User();
        user.setUsername(name);
        User savedUser = userRepository.save(user);

        return savedUser;
    }
}
