package com.ias.event.gateway;


import reactor.core.publisher.Mono;

public interface EventAssistantRepository {
    Mono<Void> save(Integer eventId, Integer assistantId);
    Mono<Boolean> existsByEventIdAndAssistantId(Integer eventId, Integer assistantId);
}
