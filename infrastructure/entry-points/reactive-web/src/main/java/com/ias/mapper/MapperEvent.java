package com.ias.mapper;

import com.ias.event.Event;
import com.ias.response.EventResponse;
import org.springframework.stereotype.Component;

@Component
public class MapperEvent {
    public EventResponse toEventResponse(Event event) {
        EventResponse response = new EventResponse();
        response.setId(event.getId());
        response.setName(event.getName());
        response.setDate(event.getDate());
        response.setLocation(event.getLocation());
        return response;
    }
}
