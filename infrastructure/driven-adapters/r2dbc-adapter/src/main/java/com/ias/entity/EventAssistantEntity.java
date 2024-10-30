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
@Table("event_assistant")
public class EventAssistantEntity {
    @Id
    private Integer id;
    private Integer eventId;
    private Integer assistantId;
}
