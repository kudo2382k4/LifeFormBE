package com.fpt.edu.lifeform.entity;

import com.fpt.edu.lifeform.entity.Parent.BaseEntity;
import jakarta.persistence.*;
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
@Table(name = "carts")
public class CartEntity extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @OneToMany(mappedBy = "cart")
    Collection<CartDetailEntity> cartDetails;
}
