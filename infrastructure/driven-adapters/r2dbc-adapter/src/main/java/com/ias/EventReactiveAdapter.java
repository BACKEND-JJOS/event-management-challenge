package com.ias;

import com.ias.entity.EventEntity;
import com.ias.event.Event;
import com.ias.repository.EventReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class EventReactiveAdapter  {

    private final EventReactiveRepository eventReactiveRepository;

    public Flux<EventEntity> getAll() {
        return eventReactiveRepository.findAll();
    }

    public Mono<EventEntity> getById(Integer id) {
        return eventReactiveRepository.findById(id);
    }

    public Mono<EventEntity> save(EventEntity eventEntity) {
        return eventReactiveRepository.save(eventEntity);
    }

    public Mono<EventEntity> update(EventEntity eventEntity) {
        return eventReactiveRepository.findById(eventEntity.getId())
                .flatMap(existingEvent -> {
                    eventEntity.setId(existingEvent.getId());
                    return eventReactiveRepository.save(eventEntity);
                });
    }

    public Mono<Void> delete(Integer id) {
        return eventReactiveRepository.deleteById(id);
    }

    public Flux<EventEntity> findAllByUserId(Integer userId) {
        return eventReactiveRepository.findAllByUserId(userId);
    }
}
