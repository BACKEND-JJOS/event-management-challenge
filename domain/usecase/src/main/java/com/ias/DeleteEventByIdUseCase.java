package com.ias;

import com.ias.event.gateway.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class DeleteEventByIdUseCase {

    private final EventRepository eventRepository;

    public Mono<Void> execute(String id){
        return eventRepository.getById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Event not found with ID: " + id)))
                .flatMap(event -> eventRepository.delete(event.getId()))
                .then();
    }
}
