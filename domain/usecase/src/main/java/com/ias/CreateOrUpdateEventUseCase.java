package com.ias;

import com.ias.event.Event;
import com.ias.event.gateway.EventGateway;
import com.ias.event.gateway.EventRepository;
import com.ias.validator.EventBusinessValidator;
import com.ias.validator.EventInputValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CreateOrUpdateEventUseCase {

    private final EventRepository eventRepository;

    private final EventGateway eventGateway;

    public Mono<Event> execute(Event event, String traceUUID) {
        return EventInputValidator.validateDateFormat(event.getDate())
                .then(EventBusinessValidator.validateDateInFuture(event.getDate()))
                .then(Mono.justOrEmpty(event.getId())
                        .flatMap(e -> eventRepository.update(event, traceUUID)
                                .flatMap(updatedEvent -> eventGateway.publishEventUpdated(updatedEvent, traceUUID)
                                        .thenReturn(updatedEvent))
                        )
                        .switchIfEmpty(eventRepository.save(event, traceUUID)
                                .flatMap(saveEvent -> eventGateway.publishEventCreated(saveEvent, traceUUID)
                                        .thenReturn(saveEvent))
                        )
                );
    }
}
