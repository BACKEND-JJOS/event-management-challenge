package com.ias;

import com.ias.event.gateway.EventRepository;
import com.ias.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class RegisterUserToEventUseCase {
    private final EventRepository eventRepository;

    public Mono<Void> register(User user, Integer eventId) {
        return eventRepository.getById(eventId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Event not found with ID: " + eventId)))
                .flatMap(existingEvent ->
                        Mono.justOrEmpty(existingEvent.getUserIds())
                                .defaultIfEmpty(new ArrayList<>())
                                .flatMap(userIds ->
                                        Mono.just(userIds.stream().anyMatch(u -> u.getId().equals(user.getId())))
                                                .filter(isAlreadyRegistered -> !isAlreadyRegistered)
                                                .switchIfEmpty(Mono.error(new IllegalArgumentException("User is already registered to the event")))
                                                .thenReturn(userIds)
                                )
                                .doOnNext(userIds -> userIds.add(user))
                                .map(userIds -> {
                                    existingEvent.setUserIds(userIds);
                                    return existingEvent;
                                })
                )
                .flatMap(eventRepository::update)
                .then();
    }
}
