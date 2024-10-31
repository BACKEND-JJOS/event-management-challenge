package com.ias;

import com.ias.entity.EventAssistantEntity;
import com.ias.repository.EventAssistantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class EventAssistantReactiveAdapter {
    private final EventAssistantRepository eventAssistantRepository;
    private final DatabaseClient databaseClient;
    public Mono<EventAssistantEntity> save(Integer eventId, Integer userId) {
        return eventAssistantRepository.save(EventAssistantEntity.builder()
                .eventId(eventId)
                .assistantId(userId)
                .build());
    }

    public Mono<Boolean> existsByEventIdAndUserId(Integer eventId, Integer assistantId) {
        return databaseClient.sql("SELECT COUNT(*) FROM event_assistant WHERE event_id = :eventId AND assistant_id = :assistantId")
                .bind("eventId", eventId)
                .bind("assistantId", assistantId)
                .map(row -> row.get(0, Integer.class) > 0)
                .one();
    }
}
