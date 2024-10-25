package com.ias.gatweway;

import com.google.gson.Gson;
import com.ias.EventReactiveAdapter;
import com.ias.event.Event;
import com.ias.event.gateway.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class EventGatewayImpl implements EventRepository {


    private final Gson mapper;


    private final EventReactiveAdapter eventReactiveAdapter;

    @Override
    public Mono<Event> getById(String id) {
        return null;
    }

    @Override
    public Flux<Event> getAll() {
        return eventReactiveAdapter.getAll()
                .map(eventsEntities -> mapper.fromJson(mapper.toJson(eventsEntities), Event.class));
    }

    @Override
    public Mono<Event> save(Event event) {
        return null;
    }

    @Override
    public Mono<Event> update(Event event) {
        return null;
    }

    @Override
    public Mono<Void> delete(String id) {
        return null;
    }
}
