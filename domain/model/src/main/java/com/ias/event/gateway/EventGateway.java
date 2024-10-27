package com.ias.event.gateway;

import com.ias.event.Event;
import reactor.core.publisher.Mono;

public interface EventGateway {
    Mono<Void> publishEventCreated(Event event, String traceUUID);
    Mono<Void> publishEventUpdated(Event event, String traceUUID);
    Mono<Void> publishEventDelete(Event event, String traceUUID);
    Mono<Void> publishUserRegisterToEvent(Integer userId, Integer eventId, String traceUUID);
}
