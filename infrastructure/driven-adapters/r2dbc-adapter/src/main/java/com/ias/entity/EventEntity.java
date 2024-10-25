package com.ias.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@Table("event")
public class EventEntity {
    private String id;
    private String name;
    private String date;
    private String location;
}
