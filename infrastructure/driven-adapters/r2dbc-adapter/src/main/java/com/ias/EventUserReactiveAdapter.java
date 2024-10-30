package com.ias;

import com.ias.entity.EventAssistantEntity;
import com.ias.repository.EventUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class EventUserReactiveAdapter {
    private final EventUserRepository eventUserRepository;
    private final DatabaseClient databaseClient;
    public Mono<EventAssistantEntity> save(Integer eventId, Integer userId) {
        return eventUserRepository.save(EventAssistantEntity.builder()
                .eventId(eventId)
                .assistantId(userId)
                .build());
    }

    public Mono<Boolean> existsByEventIdAndUserId(Integer eventId, Integer userId) {
        return databaseClient.sql("SELECT COUNT(*) FROM event_assistant WHERE event_id = :eventId AND assistant_id = :userId")
                .bind("eventId", eventId)
                .bind("userId", userId)
                .map(row -> row.get(0, Integer.class) > 0)
                .one();
    }
}
