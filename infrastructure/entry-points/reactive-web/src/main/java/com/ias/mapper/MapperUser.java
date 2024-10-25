package com.ias.mapper;

import com.ias.request.UserRequest;
import com.ias.user.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MapperUser {

    public User toDomain(UserRequest userRequest) {
        return User.builder()
                .id(userRequest.getUserId())
                .build();
    }
}
