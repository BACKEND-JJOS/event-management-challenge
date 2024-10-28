package com.ias;

import com.google.gson.Gson;
import com.ias.model.EventEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.Objects;


@Repository
@Slf4j
public class DynamoReactiveEventAdapter {
    private final DynamoDbEnhancedAsyncClient enhancedAsyncClient;
    private final DynamoDbAsyncTable<EventEntity> eventEntityDynamoDbAsyncTable;

    private final Gson mapper;

    public DynamoReactiveEventAdapter(DynamoDbEnhancedAsyncClient enhancedAsyncClient, Gson mapper) {
        this.enhancedAsyncClient = enhancedAsyncClient;
        this.eventEntityDynamoDbAsyncTable = enhancedAsyncClient.table(EventEntity.TABLE_NAME, TableSchema.fromBean(EventEntity.class));
        this.mapper = mapper;
    }

    public Mono<EventEntity> findById(String id) {
        return Mono.fromFuture(
                () -> eventEntityDynamoDbAsyncTable.getItem(getKey(id))
        ).doOnError(e -> log.error("Error when searching for the event by ID : {0}  - TRACE : {1}", id, "UUID"));
    }

    private Key getKey(String id) {
        return Key.builder().partitionValue(id).build();
    }

    public Flux<EventEntity> findAll() {
        return Flux.from(eventEntityDynamoDbAsyncTable.scan().items());
    }

    private Mono<List<EventEntity>> getAllRecords(QueryEnhancedRequest request, List<EventEntity> entityList) {
        PagePublisher<EventEntity> pagePublisher = eventEntityDynamoDbAsyncTable.scan();
        return Mono.from(pagePublisher)
                .map(pagePublisher1 -> {
                            entityList.addAll(
                                    pagePublisher1
                                            .items()
                                            .stream()
                                            .toList()
                            );
                            return pagePublisher1;
                        }
                )
                .filter(page -> Objects.nonNull(page.lastEvaluatedKey())
                )
                .flatMap(pageResponse -> getAllRecords(
                        request.toBuilder().exclusiveStartKey(pageResponse.lastEvaluatedKey()).build(),
                        entityList
                )).switchIfEmpty(Mono.just(entityList));
    }

    public Mono<EventEntity> save(EventEntity eventEntity) {
        return Mono.fromFuture(
                () -> eventEntityDynamoDbAsyncTable.putItem(eventEntity)
        ).thenReturn(eventEntity);
    }

    public Mono<Void> deleteById(String id) {
        return Mono.fromFuture(
                        () -> eventEntityDynamoDbAsyncTable.deleteItem(getKey(id))
                ).doOnError(e -> log.error("Error when deleting the event by ID : {0} - TRACE : {1}", id, "UUID"))
                .then();
    }

}
