package com.rapitskyi.security.controller;

import com.rapitskyi.security.dto.request.AuthenticationRequest;
import com.rapitskyi.security.dto.request.RegisterRequest;
import com.rapitskyi.security.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthentiocationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    ResponseEntity<String> signUp(@RequestBody @Valid RegisterRequest registerRequest) {

        authenticationService.signUp(registerRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User " + registerRequest.getFirstName() + " was successfully registered");
    }

    @PostMapping("/signIn")
    public ResponseEntity<Void> signIn(@RequestBody MultiValueMap<String, String> formData) {
        // Отримати запитуваний URL перед авторизацією

        authenticationService.signIn(AuthenticationRequest.builder()
                .email(formData.getFirst("email"))
                .password(formData.getFirst("password"))
                .build());

        return ResponseEntity.noContent().build();
    }

}

@Controller
class LoginController {
    @GetMapping("/login")
    String login() {
        return "authentication";
    }
}
