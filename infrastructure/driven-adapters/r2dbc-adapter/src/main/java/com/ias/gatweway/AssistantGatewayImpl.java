package com.ias.gatweway;

import com.google.gson.Gson;
import com.ias.AssistantReactiveAdapter;
import com.ias.entity.AssistantEntity;
import com.ias.user.User;
import com.ias.user.gateway.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AssistantGatewayImpl implements UserRepository {

    private final AssistantReactiveAdapter assistantReactiveAdapter;

    private final Gson mapper;
    @Override
    public Mono<User> getById(Integer id) {
        return assistantReactiveAdapter.getById(id)
                .map(assistantEntity -> mapper.fromJson(mapper.toJson(assistantEntity), User.class));
    }

    @Override
    public Mono<User> save(User user) {
        return assistantReactiveAdapter.save(mapper.fromJson(mapper.toJson(user), AssistantEntity.class))
                .map(assistantEntity -> mapper.fromJson(mapper.toJson(assistantEntity), User.class));
    }

}
