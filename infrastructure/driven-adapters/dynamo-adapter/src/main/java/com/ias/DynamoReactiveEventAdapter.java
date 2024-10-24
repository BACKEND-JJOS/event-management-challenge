package com.ias;

import com.ias.model.EventEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;


@Repository
@Slf4j
public class DynamoReactiveEventAdapter {
    private final DynamoDbEnhancedAsyncClient enhancedAsyncClient;
    private final DynamoDbAsyncTable<EventEntity> userEntityDynamoDbAsyncTable;

    public DynamoReactiveEventAdapter(DynamoDbEnhancedAsyncClient enhancedAsyncClient) {
        this.enhancedAsyncClient = enhancedAsyncClient;
        this.userEntityDynamoDbAsyncTable = enhancedAsyncClient.table(EventEntity.TABLE_NAME, TableSchema.fromBean(EventEntity.class));
    }
}
