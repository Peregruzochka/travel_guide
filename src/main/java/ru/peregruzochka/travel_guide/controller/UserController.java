package ru.peregruzochka.travel_guide.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.peregruzochka.travel_guide.model.User;
import ru.peregruzochka.travel_guide.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestParam String name) {
        User user = userService.createUser(name);
        HttpHeaders headers = new HttpHeaders();
        headers.add("app-user-id", user.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
