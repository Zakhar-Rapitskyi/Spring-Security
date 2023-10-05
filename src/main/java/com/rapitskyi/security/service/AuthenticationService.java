package com.rapitskyi.security.service;

import com.rapitskyi.security.dto.request.AuthenticationRequest;
import com.rapitskyi.security.dto.request.RegisterRequest;
import com.rapitskyi.security.entity.User;
import com.rapitskyi.security.exception.ConflictException;
import com.rapitskyi.security.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public void signUp(RegisterRequest request) {
        String email = request.getEmail();

        if (userService.existsByEmail(email))
            throw new ConflictException("This email alrthewfbbeibfeady in use");

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        if (request.getRole() != null)
            user.setRole("MANAGER");
        else
            user.setRole("USER");

        userService.save(user);
    }

    public void signIn(AuthenticationRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        if (!userService.existsByEmail(email))
            throw new UnauthorizedException("There is no user with this email. Please sign up");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
