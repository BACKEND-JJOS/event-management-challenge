package com.ias;

import com.ias.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserAuthenticationUseCase {

    private final ReactiveUserDetailsService userDetailsService;

    public Mono<Auth> authenticate(String userName, String password) {
        return userDetailsService.findByUsername(userName)
                .flatMap(userDetails -> new BCryptPasswordEncoder().matches(password, userDetails.getPassword())
                        ? Mono.just(new Auth(userName, userDetails.getAuthorities(), true))
                        : Mono.error(new BadCredentialsException("Invalid username or password")));
    }

}
