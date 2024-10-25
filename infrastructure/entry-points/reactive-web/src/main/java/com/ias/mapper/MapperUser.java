package com.ias.mapper;

import com.ias.request.UserRequest;
import com.ias.user.User;
import org.springframework.stereotype.Component;

@Component
public class MapperUser {

    public User toDomain(UserRequest userRequest) {
        return User.builder()
                .id(userRequest.getUserId())
                .build();
    }
}
