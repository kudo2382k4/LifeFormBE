package com.fpt.edu.lifeform.Entity;

import com.fpt.edu.lifeform.Entity.Parent.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "color")
public class ColorEntity extends BaseEntity {
    @Column(nullable = false, unique = true, length = 50)
    String name;

    @OneToMany(mappedBy = "color")
    Collection<OrderDetailEntity> orderDetails;

    @ManyToMany
    Collection<ProductEntity> products;
}
