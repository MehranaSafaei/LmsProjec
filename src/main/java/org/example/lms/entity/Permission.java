package org.example.lms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "PERMISSIONS")
public class Permission extends AbstractPermission {
}
