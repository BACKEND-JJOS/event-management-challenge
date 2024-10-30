package com.ias.gatweway;

import com.google.gson.Gson;
import com.ias.AssistantReactiveAdapter;
import com.ias.entity.AssistantEntity;
import com.ias.user.Assistant;
import com.ias.user.gateway.AssistantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AssistantGatewayImpl implements AssistantRepository {

    private final AssistantReactiveAdapter assistantReactiveAdapter;

    private final Gson mapper;
    @Override
    public Mono<Assistant> getById(Integer id) {
        return assistantReactiveAdapter.getById(id)
                .map(assistantEntity -> mapper.fromJson(mapper.toJson(assistantEntity), Assistant.class));
    }

    @Override
    public Mono<Assistant> save(Assistant assistant) {
        return assistantReactiveAdapter.save(mapper.fromJson(mapper.toJson(assistant), AssistantEntity.class))
                .map(assistantEntity -> mapper.fromJson(mapper.toJson(assistantEntity), Assistant.class));
    }

}
