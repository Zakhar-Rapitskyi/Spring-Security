package com.rapitskyi.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AuthenticationProvider customProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)

                .securityContext(context -> context.requireExplicitSave(false))

                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                .authorizeHttpRequests(request -> request
                        .requestMatchers("/", "/signUp", "/signIn", "/logout").permitAll()
                        .anyRequest().authenticated())

                .formLogin(c->c.loginPage("/login").permitAll()
                        .loginProcessingUrl("/signIn")
                        .defaultSuccessUrl("/hello",false))

                .logout(logout -> logout.logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(((request, response, authentication) ->
                                SecurityContextHolder.clearContext())))
                .authenticationProvider(customProvider);

        return http.build();
    }

}


