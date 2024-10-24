package com.ias;

import com.ias.model.EventEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Optional;


@Repository
@Slf4j
public class DynamoReactiveEventAdapter {
    private final DynamoDbEnhancedAsyncClient enhancedAsyncClient;
    private final DynamoDbAsyncTable<EventEntity> eventEntityDynamoDbAsyncTable;

    public DynamoReactiveEventAdapter(DynamoDbEnhancedAsyncClient enhancedAsyncClient) {
        this.enhancedAsyncClient = enhancedAsyncClient;
        this.eventEntityDynamoDbAsyncTable = enhancedAsyncClient.table(EventEntity.TABLE_NAME, TableSchema.fromBean(EventEntity.class));
    }

    public Mono<EventEntity> findById(String id) {
        return Mono.fromFuture(
                () ->  eventEntityDynamoDbAsyncTable.getItem(getKey(id))
        ).doOnError(e ->  log.error("Error when searching for the event by ID : {0}  - TRACE : {1}", id, "UUID"));
    }

    private Key getKey(String id) {
        return Key.builder().partitionValue(id).build();
    }

    public Flux<EventEntity> findAll() {
        return Flux.from(eventEntityDynamoDbAsyncTable.scan().items());
    }

    public Mono<EventEntity> save(EventEntity eventEntity) {
        return Mono.fromFuture(
                () -> eventEntityDynamoDbAsyncTable.putItem(eventEntity)
        ).thenReturn(eventEntity);
    }
}
