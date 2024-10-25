package com.ias;

import com.ias.event.gateway.EventRepository;
import com.ias.event.gateway.EventUserRepository;
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

    public Mono<Void> register(User user, Integer eventId) {
        return eventRepository.getById(eventId)
                .switchIfEmpty(Mono.error(new RuntimeException("Event not found")))
                .flatMap(event ->
                        userRepository.getById(user.getId())
                                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                                .flatMap(existingUser ->
                                        eventUserRepository.existsByEventIdAndUserId(existingUser.getId(), eventId)
                                                .flatMap(userRegisteredInEvent -> userRegisteredInEvent ?
                                                        Mono.error(new RuntimeException("The user was already registered in the event previously")) :
                                                        eventUserRepository.save(event.getId(), existingUser.getId()).then()
                                                )
                                )

                );
    }
}
