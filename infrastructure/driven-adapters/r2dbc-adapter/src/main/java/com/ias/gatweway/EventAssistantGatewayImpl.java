package com.ias.gatweway;

import com.ias.EventUserReactiveAdapter;
import com.ias.event.gateway.EventUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class EventUserGatewayImpl implements EventUserRepository {

    private final EventUserReactiveAdapter eventUserAdapter;

    @Override
    public Mono<Void> save(Integer eventId, Integer userId) {
        return eventUserAdapter.save(eventId, userId)
                .then();
    }

    @Override
    public Mono<Boolean> existsByEventIdAndUserId(Integer eventId, Integer userId) {
        return eventUserAdapter.existsByEventIdAndUserId(eventId,userId);
    }
}
