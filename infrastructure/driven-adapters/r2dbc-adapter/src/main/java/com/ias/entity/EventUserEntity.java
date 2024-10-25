package com.ias.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table("event_user")
public class EventUserEntity {
    @Id
    private Integer id;
    private Integer eventId;
    private Integer userId;
}
