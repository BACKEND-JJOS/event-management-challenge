package com.ias;

import com.ias.entity.UserEntity;
import com.ias.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserReactiveAdapter {

    private final UserRepository userRepository;

    public Mono<UserEntity> getById(Integer id) {
        return userRepository.findById(id);
    }

    public Mono<UserEntity> save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
