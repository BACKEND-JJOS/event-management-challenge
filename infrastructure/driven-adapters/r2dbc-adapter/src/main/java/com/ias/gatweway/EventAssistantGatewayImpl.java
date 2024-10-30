package com.ias.gatweway;

import com.ias.EventUserReactiveAdapter;
import com.ias.event.gateway.EventAssistantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class EventAssistantGatewayImpl implements EventAssistantRepository {

    private final EventUserReactiveAdapter eventUserAdapter;

    @Override
    public Mono<Void> save(Integer eventId, Integer assistantId) {
        return eventUserAdapter.save(eventId, assistantId)
                .then();
    }

    @Override
    public Mono<Boolean> existsByEventIdAndAssistantId(Integer eventId, Integer assistantId) {
        return eventUserAdapter.existsByEventIdAndUserId(eventId, assistantId);
    }
}
