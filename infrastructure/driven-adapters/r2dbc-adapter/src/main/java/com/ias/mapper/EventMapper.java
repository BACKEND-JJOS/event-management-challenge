package com.ias.mapper;

import com.ias.entity.EventEntity;
import com.ias.event.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public Event toDomain(EventEntity eventEntity) {
        if (eventEntity == null) {
            return null;
        }

        return Event.builder()
                .id(eventEntity.getId())
                .name(eventEntity.getName())
                .date(eventEntity.getDate())
                .location(eventEntity.getLocation())
                .build();
    }

    public EventEntity toEntity(Event event) {
        if (event == null) {
            return null;
        }
        return EventEntity.builder()
                .id(event.getId())
                .name(event.getName())
                .date(event.getDate())
                .location(event.getLocation())
                .build();
    }
}
