package com.ias;

import com.ias.event.gateway.EventGateway;
import com.ias.event.gateway.EventRepository;
import com.ias.event.gateway.EventAssistantRepository;
import com.ias.exception.BusinessException;
import com.ias.exception.BusinessEventErrorCode;
import com.ias.user.Assistant;
import com.ias.user.gateway.AssistantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class RegisterAssistantToEventUseCase {
    private final EventRepository eventRepository;

    private final AssistantRepository assistantRepository;

    private final EventAssistantRepository eventAssistantRepository;

    private final EventGateway eventGateway;

    public Mono<Void> register(Assistant assistant, Integer eventId, String traceUUID) {
        return eventRepository.getById(eventId, traceUUID)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessEventErrorCode.EVENT_NOT_FOUND)))
                .flatMap(event ->
                        assistantRepository.getById(assistant.getId())
                                .switchIfEmpty(Mono.error(new BusinessException(BusinessEventErrorCode.USER_ASSISTANT_NOT_FOUND)))
                                .flatMap(existingUser ->
                                        eventAssistantRepository.existsByEventIdAndAssistantId(existingUser.getId(), eventId)
                                                .flatMap(userRegisteredInEvent -> Boolean.TRUE.equals(userRegisteredInEvent) ?
                                                        Mono.error(new BusinessException(BusinessEventErrorCode.USER_ASSISTANT_ALREADY_REGISTERED)) :
                                                        eventAssistantRepository.save(event.getId(), existingUser.getId())
                                                                .then(eventGateway.publishUserRegisterToEvent(existingUser.getId(), event.getId(), traceUUID))
                                                )
                                )

                );
    }
}
