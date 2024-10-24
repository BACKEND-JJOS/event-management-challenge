package com.ias.user.gateway;

import com.ias.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> getById(String id);
}
