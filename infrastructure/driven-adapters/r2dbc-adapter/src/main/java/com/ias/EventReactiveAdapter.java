package com.ias;

import com.ias.entity.EventEntity;
import com.ias.repository.EventReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
@Slf4j
public class EventReactiveAdapter {

    private final EventReactiveRepository eventReactiveRepository;

    private static final String MESSAGE_LOG_TRACE = "ADAPTER RUN {} WITH TRACE {}";

    public Flux<EventEntity> getAll(String traceUUID) {
        return eventReactiveRepository.findAll()
                .doOnSubscribe(subscription -> log.debug(MESSAGE_LOG_TRACE, "get_all", traceUUID))
                .doOnError(error -> log.error(MESSAGE_LOG_TRACE, error, traceUUID));
    }

    public Mono<EventEntity> getById(Integer id, String traceUUID) {
        return eventReactiveRepository.findById(id)
                .doOnSubscribe(subscription -> log.debug(MESSAGE_LOG_TRACE, "get_by_id", traceUUID))
                .doOnError(error -> log.error(MESSAGE_LOG_TRACE, error, traceUUID));
    }

    public Mono<EventEntity> save(EventEntity eventEntity, String traceUUID) {
        return eventReactiveRepository.save(eventEntity)
                .doOnSubscribe(subscription -> log.debug(MESSAGE_LOG_TRACE, "save", traceUUID))
                .doOnError(error -> log.error(MESSAGE_LOG_TRACE, error, traceUUID));
    }

    public Mono<EventEntity> update(EventEntity eventEntity, String traceUUID) {
        return eventReactiveRepository.findById(eventEntity.getId())
                .flatMap(existingEvent -> {
                    eventEntity.setId(existingEvent.getId());
                    return eventReactiveRepository.save(eventEntity);
                })
                .doOnSubscribe(subscription -> log.debug(MESSAGE_LOG_TRACE, "update", traceUUID))
                .doOnError(error -> log.error(MESSAGE_LOG_TRACE, error, traceUUID));
    }

    public Mono<Void> delete(Integer id, String traceUUID) {
        return eventReactiveRepository.deleteById(id)
                .doOnSubscribe(subscription -> log.debug(MESSAGE_LOG_TRACE, "delete", traceUUID))
                .doOnError(error -> log.error(MESSAGE_LOG_TRACE, error, traceUUID));
    }

    public Flux<EventEntity> findAllByUserId(Integer userId, String traceUUID) {
        return eventReactiveRepository.findAllByUserId(userId)
                .doOnSubscribe(subscription -> log.debug(MESSAGE_LOG_TRACE, "find_all_by_user_id", traceUUID))
                .doOnError(error -> log.error(MESSAGE_LOG_TRACE, error, traceUUID));
    }
}
