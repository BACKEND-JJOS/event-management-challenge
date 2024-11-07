package com.ias;

import com.ias.entity.AssistantEntity;
import com.ias.repository.AssistantRepository;
import com.ias.user.Assistant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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

    public Flux<AssistantEntity> getAllAssistanByEventId(Integer eventId) {
        return assistantRepository.getAllAssistantByEventId(eventId);
    }
}
