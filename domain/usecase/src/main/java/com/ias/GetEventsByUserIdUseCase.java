package com.ias;

import com.ias.event.Event;
import com.ias.event.gateway.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class GetEventsByUserIdUseCase {

    private final EventRepository eventRepository;

    public Flux<Event> execute(Integer userId) {
        return eventRepository.getEventsByUserId(userId);
    }
}
