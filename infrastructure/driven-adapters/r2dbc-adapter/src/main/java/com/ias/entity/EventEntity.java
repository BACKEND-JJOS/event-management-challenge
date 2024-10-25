package com.ias.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("event")
public class EventEntity {
    @Id
    private Integer id;
    private String name;
    private String date;
    private String location;
}
