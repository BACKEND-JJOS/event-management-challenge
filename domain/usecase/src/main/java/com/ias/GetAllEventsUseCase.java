package com.ias;

import com.ias.event.Event;
import com.ias.event.gateway.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class GetAllEventsUseCase {

    private  final EventRepository eventRepository;

    public Flux<Event> get(){
        return eventRepository.getAll();
    }
}
