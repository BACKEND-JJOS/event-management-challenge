package com.ias.request;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class EventRequest {
    private Integer id;
    private String name;
    private String date;
    private String location;
}
