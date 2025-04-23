package org.example.lms.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@MappedSuperclass
public class AbstractPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String key;
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();

    public AbstractPermission(String name, String key, Set<Role> roles) {
        this.name = name;
        this.key = key;
        this.roles = roles;
    }
}
