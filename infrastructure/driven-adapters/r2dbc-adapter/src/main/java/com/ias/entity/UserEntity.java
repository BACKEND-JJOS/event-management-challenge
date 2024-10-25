package com.ias.entity;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table("user")
public class UserEntity {
    private String id;
}
