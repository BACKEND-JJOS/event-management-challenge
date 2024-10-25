package com.ias;

import com.ias.entity.EventEntity;
import com.ias.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class EventReactiveAdapter {

    private final EventRepository eventRepository;

    public Flux<EventEntity> getAll() {
        return eventRepository.findAll();
    }

    public Mono<EventEntity> getById(Integer id) {
        return eventRepository.findById(id);
    }

    public Mono<EventEntity> save(EventEntity eventEntity) {
        System.out.println("aquiii" + eventEntity);
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
}
