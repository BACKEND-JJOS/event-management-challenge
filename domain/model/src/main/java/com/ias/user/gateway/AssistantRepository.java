package com.ias.user.gateway;

import com.ias.user.Assistant;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AssistantRepository {
    Mono<Assistant> getById(Integer id);

    Mono<Assistant> save(Assistant use);

    Flux<Assistant> getAllAssistantByEventId(Integer eventId);
}
