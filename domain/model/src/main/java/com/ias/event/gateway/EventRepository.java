package com.ias.event.gateway;


import com.ias.event.Event;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventRepository {
    Flux<Event> getAll();

    Mono<Event> getById(String id);

    Flux<Event> getAllByUserId(String id);
    Mono<Event> save(Event event);

    Mono<Event> update(Event event);

    Mono<Void> delete(String id);
}
