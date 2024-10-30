package com.ias.response;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class EventResponse {
    private Integer id;
    private String name;
    private String date;
    private String location;
}
