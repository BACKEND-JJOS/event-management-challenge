package com.ias;

import com.ias.event.Event;
import com.ias.event.gateway.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CreateOrUpdateEventUseCase {

    private final EventRepository eventRepository;

    public Mono<Event> execute(Event event){
     return Mono.justOrEmpty(event.getId())
             .flatMap(e -> eventRepository.update(event))
             .switchIfEmpty(
                     generateUUID()
                             .flatMap(uuid -> {
                                 event.setId(uuid);
                                 return eventRepository.save(event);
                             })
             );

    }

    private Mono<String> generateUUID() {
        return Mono.fromSupplier(UUID::randomUUID)
                .map(UUID::toString);
    }
}
