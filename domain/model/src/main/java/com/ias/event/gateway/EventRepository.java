package com.ias.event.gateway;


import com.ias.event.Event;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventRepository {
    Flux<Event> getAll(String traceUUID);

    Mono<Event> getById(Integer id, String traceUUID);

    Mono<Event> save(Event event, String traceUUID);

    Mono<Event> update(Event event, String traceUUID);

    Mono<Void> delete(Integer id, String traceUUID);

    Flux<Event> getEventsByUserId(Integer userId, String traceUUID);
}
