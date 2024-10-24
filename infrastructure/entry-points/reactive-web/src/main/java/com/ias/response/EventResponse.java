package com.ias.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EventResponse {
    private String id;
    private String name;
    private String status;
    private String location;
}
