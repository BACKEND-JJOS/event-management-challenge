package com.ias.gateway;

import com.ias.config.EventRabbitConfig;
import com.ias.event.Event;
import com.ias.event.gateway.EventGateway;
import com.ias.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitEventsGatewayImpl implements EventGateway {

    private final RabbitTemplate rabbitTemplate;

    private Mono<Void> sendMessage(String routingKey, Object payload, String traceUUID) {
        return Mono.defer(() -> {
                    rabbitTemplate.convertAndSend(
                            EventRabbitConfig.EVENT_EXCHANGE,
                            routingKey,
                            new Message<>(traceUUID, payload)
                    );
                    log.debug("RABBIT RUN {} WITH TRACE {}", routingKey, traceUUID);
                    return Mono.empty();
                })
                .then()
                .doOnError(e -> log.error("Error sending message to queue with routing key {}: {} WITH TRACE {}", routingKey, e.getMessage(), traceUUID));
    }

    @Override
    public Mono<Void> publishEventCreated(Event event, String traceUUID) {
        return sendMessage(EventRabbitConfig.EVENT_CREATED_ROUTING_KEY, event, traceUUID);
    }

    @Override
    public Mono<Void> publishEventUpdated(Event event, String traceUUID) {
        return sendMessage(EventRabbitConfig.EVENT_UPDATED_ROUTING_KEY, event, traceUUID);
    }

    @Override
    public Mono<Void> publishEventDelete(Event event, String traceUUID) {
        return sendMessage(EventRabbitConfig.EVENT_DELETED_ROUTING_KEY, event, traceUUID);
    }

    @Override
    public Mono<Void> publishUserRegisterToEvent(Integer userId, Integer eventId, String traceUUID) {
        return sendMessage(EventRabbitConfig.EVENT_USER_REGISTER_ROUTING_KEY, Map.of("userId", userId, "eventId", eventId), traceUUID);
    }
}
