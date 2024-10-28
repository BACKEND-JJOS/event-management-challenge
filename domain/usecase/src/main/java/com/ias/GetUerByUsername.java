package com.ias;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetUerByUsername implements ReactiveUserDetailsService {
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        String pass = new BCryptPasswordEncoder().encode("user");
        System.out.println(pass);
        return Mono.just(User.withUsername("user")
                .password(pass)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .authorities("READ", "WRITE")
                .build()
        );
    }
}
