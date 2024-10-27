package com.ias;

import com.ias.event.gateway.EventGateway;
import com.ias.event.gateway.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class DeleteEventByIdUseCase {

    private final EventRepository eventRepository;

    private final EventGateway eventGateway;

    public Mono<Void> execute(Integer id, String traceUUID) {
        return eventRepository.getById(id, traceUUID)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Event not found with ID: " + id)))
                .flatMap(event -> eventRepository.delete(event.getId(), traceUUID)
                        .then(eventGateway.publishEventDelete(event, traceUUID))
                )
                .then();
    }
}
