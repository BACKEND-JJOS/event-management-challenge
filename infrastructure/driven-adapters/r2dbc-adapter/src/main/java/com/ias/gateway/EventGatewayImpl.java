package com.ias.gateway;

import com.ias.EventReactiveAdapter;
import com.ias.entity.EventEntity;
import com.ias.event.Event;
import com.ias.event.gateway.EventRepository;
import com.ias.mapper.EventMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class EventGatewayImpl implements EventRepository {


    private final EventReactiveAdapter eventReactiveAdapter;
    private final EventMapper eventMapper;

    @Override
    public Flux<Event> getAll(String traceUUID) {
        return eventReactiveAdapter.getAll(traceUUID)
                .map(eventMapper::toDomain);
    }

    @Override
    public Mono<Event> getById(Integer id, String traceUUID) {
        return eventReactiveAdapter.getById(id,traceUUID)
                .map(eventMapper::toDomain);
    }

    @Override
    public Mono<Event> save(Event event, String traceUUID) {
        return eventReactiveAdapter.save(eventMapper.toEntity(event), traceUUID)
                .map(eventMapper::toDomain);
    }

    @Override
    public Mono<Event> update(Event event, String traceUUID) {
        EventEntity eventEntity = eventMapper.toEntity(event);
        return eventReactiveAdapter.update(eventEntity, traceUUID)
                .map(eventMapper::toDomain);
    }

    @Override
    public Mono<Void> delete(Integer id, String traceUUID) {
        return eventReactiveAdapter.delete(id, traceUUID);
    }

    @Override
    public Flux<Event> getEventsByUserId(Integer userId, String traceUUID) {
        return eventReactiveAdapter.findAllByUserId(userId,traceUUID)
                .map(eventMapper::toDomain);
    }
}
