package com.ias;

import com.ias.entity.EventEntity;
import com.ias.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@RequiredArgsConstructor
@Service
public class EventReactiveAdapter {

    private final EventRepository eventRepository;

    public Flux<EventEntity> getAll() {
        return eventRepository.findAll()
                .doOnNext(System.out::print);
    }

}
