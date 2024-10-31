package com.ias.repository;

import com.ias.entity.EventAssistantEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventAssistantRepository extends R2dbcRepository<EventAssistantEntity, Integer> {
}
