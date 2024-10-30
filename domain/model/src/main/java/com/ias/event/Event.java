package com.ias.event;

import com.ias.user.Assistant;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Event {

    private Integer id;
    private String name;
    private String date;
    private String location;
    private List<Assistant> assistantIds;
}
