package com.ias.model;

import com.ias.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@Getter
@Setter
@ToString
@DynamoDbBean
public class EventEntity {

    public static final String TABLE_NAME = "event";

    private String id;
    private String name;
    private String date;
    private String location;
    private List<UserEntity> userIds;

    @DynamoDbPartitionKey
    public String getId() {
        return this.id;
    }
}
