package com.fpt.edu.lifeform.entity;

import com.fpt.edu.lifeform.entity.Parent.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "cart_detail")
public class CartDetailEntity extends BaseEntity {
    int quantity;

    @ManyToOne
    ProductEntity product;

    @ManyToOne
    CartEntity cart;
}
