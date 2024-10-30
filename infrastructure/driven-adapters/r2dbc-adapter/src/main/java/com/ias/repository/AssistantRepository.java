package com.ias.repository;

import com.ias.entity.AssistantEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssistantRepository extends R2dbcRepository<AssistantEntity, Integer> {
}
