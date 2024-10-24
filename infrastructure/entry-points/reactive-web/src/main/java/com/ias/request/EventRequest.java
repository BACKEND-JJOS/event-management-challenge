package com.ias.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EventRequest {
    private String id;
    private String name;
    private String date;
    private String location;
}
