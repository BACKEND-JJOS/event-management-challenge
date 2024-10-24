package com.ias.event;

import com.ias.user.User;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Event {

    private String id;
    private String name;
    private String date;
    private String location;
    private List<User> userIds;
}
