package com.ias;

import com.ias.event.Event;
import com.ias.event.gateway.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetEventByIdUseCase {

    private final EventRepository eventRepository;

    public Mono<Event> get(Integer id, String traceUUID){
        return  eventRepository.getById(id,traceUUID);
    }

}
