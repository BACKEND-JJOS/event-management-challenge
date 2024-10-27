package com.ias.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message<T> {
    private String traceUUID;
    private T data;
}
