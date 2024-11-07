package com.ias.repository;

import com.ias.entity.AssistantEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AssistantRepository extends R2dbcRepository<AssistantEntity, Integer> {
    @Query("SELECT a.* FROM assistant a " +
            "JOIN event_assistant ea ON a.id = ea.assistant_id " +
            "WHERE ea.event_id = :eventId")
    Flux<AssistantEntity> getAllAssistantByEventId(Integer eventId);
}
