package com.ias.gatweway;

import com.google.gson.Gson;
import com.ias.UserReactiveAdapter;
import com.ias.entity.UserEntity;
import com.ias.user.User;
import com.ias.user.gateway.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class UserGatewayImpl implements UserRepository {

    private final UserReactiveAdapter userReactiveAdapter;

    private final Gson mapper;
    @Override
    public Mono<User> getById(Integer id) {
        return userReactiveAdapter.getById(id)
                .map(userEntity -> mapper.fromJson(mapper.toJson(userEntity), User.class));
    }

    @Override
    public Mono<User> save(User user) {
        return userReactiveAdapter.save(mapper.fromJson(mapper.toJson(user), UserEntity.class))
                .map(userEntity -> mapper.fromJson(mapper.toJson(userEntity), User.class));
    }

}
