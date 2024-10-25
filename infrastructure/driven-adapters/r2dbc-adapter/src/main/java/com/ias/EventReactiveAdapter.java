package com.ias;

import com.ias.entity.EventEntity;
import com.ias.event.Event;
import com.ias.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class EventReactiveAdapter {

    private final EventRepository eventRepository;
    private final DatabaseClient databaseClient;

    public Flux<EventEntity> getAll() {
        return eventRepository.findAll();
    }

    public Mono<EventEntity> getById(Integer id) {
        return eventRepository.findById(id);
    }

    public Mono<EventEntity> save(EventEntity eventEntity) {
        return eventRepository.save(eventEntity);
    }

    public Mono<EventEntity> update(EventEntity eventEntity) {
        return eventRepository.findById(eventEntity.getId())
                .flatMap(existingEvent -> {
                    eventEntity.setId(existingEvent.getId());
                    return eventRepository.save(eventEntity);
                });
    }

    public Mono<Void> delete(Integer id) {
        return eventRepository.deleteById(id);
    }

    public Flux<Event> findAllByUserId(Integer userId) {
        return null;
    }
}
