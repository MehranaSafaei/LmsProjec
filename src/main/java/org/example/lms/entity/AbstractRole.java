package org.example.lms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.lms.entity.enums.UserRole;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@MappedSuperclass
@AllArgsConstructor
public class AbstractRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
