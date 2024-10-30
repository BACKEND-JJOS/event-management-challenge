package com.ias.mapper;

import com.ias.request.UserRequest;
import com.ias.user.Assistant;
import org.springframework.stereotype.Component;

@Component
public class MapperUser {

    public Assistant toDomain(UserRequest userRequest) {
        return Assistant.builder()
                .id(userRequest.getUserId())
                .build();
    }
}
