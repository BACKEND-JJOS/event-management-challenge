package com.ias.mapper;

import com.ias.entity.EventEntity;
import com.ias.entity.UserEntity;
import com.ias.event.Event;
import com.ias.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    public Event toDomain(EventEntity eventEntity) {
        if (eventEntity == null) {
            return null;
        }

        return Event.builder()
                .id(eventEntity.getId())
                .name(eventEntity.getName())
                .date(eventEntity.getDate())
                .location(eventEntity.getLocation())
                .build();
    }

    private List<User> toDomainUserList(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private User toDomain(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return User.builder()
                .id(userEntity.getId())
                .build();
    }
}
