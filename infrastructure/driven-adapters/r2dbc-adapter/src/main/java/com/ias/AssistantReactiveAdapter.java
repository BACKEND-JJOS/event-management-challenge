package com.ias;

import com.ias.entity.AssistantEntity;
import com.ias.repository.AssistantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AssistantReactiveAdapter {

    private final AssistantRepository assistantRepository;

    public Mono<AssistantEntity> getById(Integer id) {
        return assistantRepository.findById(id);
    }

    public Mono<AssistantEntity> save(AssistantEntity assistantEntity) {
        return assistantRepository.save(assistantEntity);
    }
}
