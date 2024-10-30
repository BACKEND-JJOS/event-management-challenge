package com.ias;

import com.ias.event.gateway.EventGateway;
import com.ias.event.gateway.EventRepository;
import com.ias.event.gateway.EventUserRepository;
import com.ias.exception.BusinessException;
import com.ias.exception.BusinessEventErrorCode;
import com.ias.user.User;
import com.ias.user.gateway.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class RegisterUserToEventUseCase {
    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    private final EventUserRepository eventUserRepository;

    private final EventGateway eventGateway;

    public Mono<Void> register(User user, Integer eventId, String traceUUID) {
        return eventRepository.getById(eventId, traceUUID)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessEventErrorCode.EVENT_NOT_FOUND)))
                .flatMap(event ->
                        userRepository.getById(user.getId())
                                .switchIfEmpty(Mono.error(new BusinessException(BusinessEventErrorCode.USER_NOT_FOUND)))
                                .flatMap(existingUser ->
                                        eventUserRepository.existsByEventIdAndUserId(existingUser.getId(), eventId)
                                                .flatMap(userRegisteredInEvent -> Boolean.TRUE.equals(userRegisteredInEvent) ?
                                                        Mono.error(new BusinessException(BusinessEventErrorCode.USER_ALREADY_REGISTERED)) :
                                                        eventUserRepository.save(event.getId(), existingUser.getId())
                                                                .then(eventGateway.publishUserRegisterToEvent(event.getId(), existingUser.getId(), traceUUID))
                                                )
                                )

                );
    }
}
