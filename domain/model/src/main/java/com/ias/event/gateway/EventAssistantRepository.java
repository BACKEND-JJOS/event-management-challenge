package com.ias.event.gateway;


import reactor.core.publisher.Mono;

public interface EventUserRepository {
    Mono<Void> save(Integer eventId, Integer userId);
    Mono<Boolean> existsByEventIdAndUserId(Integer eventId, Integer userId);
}
