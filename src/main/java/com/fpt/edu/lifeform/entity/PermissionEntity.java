package com.fpt.edu.lifeform.entity;

import com.fpt.edu.lifeform.entity.Parent.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permissions")
public class PermissionEntity extends BaseEntity {

    @Column(length = 255, nullable = false)
    String apiPath;

    @Column(length = 255, nullable = false)
    String apiMethod;

    @ManyToMany
    Collection<RoleEntity> entities;
}
