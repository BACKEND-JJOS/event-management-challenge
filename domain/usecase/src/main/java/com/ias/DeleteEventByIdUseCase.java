package com.ias;

import com.ias.event.gateway.EventAssistantRepository;
import com.ias.event.gateway.EventGateway;
import com.ias.event.gateway.EventRepository;
import com.ias.exception.BusinessEventErrorCode;
import com.ias.exception.BusinessException;
import com.ias.user.gateway.AssistantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class DeleteEventByIdUseCase {

    private final EventRepository eventRepository;

    private final AssistantRepository assistantRepository;

    private final EventGateway eventGateway;

    public Mono<Void> execute(Integer id, String traceUUID) {
        return eventRepository.getById(id, traceUUID)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessEventErrorCode.EVENT_NOT_FOUND)))
                .flatMap(event -> assistantRepository.getAllAssistantByEventId(id)
                        .hasElements()
                        .flatMap(hasAssistants -> Mono.just(hasAssistants)
                                .filter(exists -> !exists)
                                .switchIfEmpty(Mono.error(new BusinessException(BusinessEventErrorCode.EVENT_HAS_ASSISTANT)))
                                .then(eventRepository.delete(event.getId(), traceUUID)
                                        .then(eventGateway.publishEventDelete(event, traceUUID)))
                        )
                )
                .then();
    }
}
