package com.rapitskyi.security.controller;

import com.rapitskyi.security.entity.User;
import com.rapitskyi.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloController {
    private final UserService userService;

    @GetMapping("/hello")
    ResponseEntity<String> hello() {
        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok("Hello, " + user.getFirstName() + ' ' + user.getLastName());
    }

    @GetMapping("/")
    ResponseEntity<String> defaultPage() {
        return ResponseEntity.ok("Welcome to motherfucker");
    }
}
