package com.ias.repository;

import com.ias.entity.EventEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EventReactiveRepository extends R2dbcRepository<EventEntity, Integer> {
    @Query("SELECT e.* FROM event e JOIN event_assistant eu ON e.id = eu.event_id WHERE eu.assistant_id = :userId")
    Flux<EventEntity> findAllByUserId(Integer userId);
}
