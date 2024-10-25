package com.ias.gatweway;

import com.ias.EventReactiveAdapter;
import com.ias.entity.EventEntity;
import com.ias.event.Event;
import com.ias.event.gateway.EventRepository;
import com.ias.mapper.EventMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class EventGatewayImpl implements EventRepository {

    private final EventReactiveAdapter eventReactiveAdapter;
    private final EventMapper eventMapper;

    @Override
    public Flux<Event> getAll() {
        return eventReactiveAdapter.getAll()
                .map(eventMapper::toDomain);
    }

    @Override
    public Mono<Event> getById(Integer id) {
        return eventReactiveAdapter.getById(id)
                .map(eventMapper::toDomain);
    }

    @Override
    public Mono<Event> save(Event event) {
        return eventReactiveAdapter.save(eventMapper.toEntity(event))
                .map(eventMapper::toDomain);
    }

    @Override
    public Mono<Event> update(Event event) {
        EventEntity eventEntity = eventMapper.toEntity(event);
        return eventReactiveAdapter.update(eventEntity)
                .map(eventMapper::toDomain);
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return eventReactiveAdapter.delete(id);
    }

    @Override
    public Flux<Event> getEventsByUserId(Integer userId) {
        return eventReactiveAdapter.findAllByUserId(userId)
                .map(eventMapper::toDomain);
    }
}
