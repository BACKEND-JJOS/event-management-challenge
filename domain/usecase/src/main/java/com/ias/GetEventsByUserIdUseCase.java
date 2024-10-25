package com.ias;

import com.ias.event.Event;
import com.ias.event.gateway.EventRepository;
import com.ias.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class GetEventsByUserIdUseCase {

    private final EventRepository eventRepository;

    public Flux<Event> execute(String userId) {
        return eventRepository.getAll()
                .filter(event ->
                        event.getUserIds() != null &&
                                event.getUserIds().stream()
                                        .map(User::getId)
                                        .anyMatch(id -> id.equals(userId))
                );
    }
}
