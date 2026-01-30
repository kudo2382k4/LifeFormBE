package com.fpt.edu.lifeform.entity;

import com.fpt.edu.lifeform.entity.Parent.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
@Table(name = "cart_details")
public class CartDetailEntity extends BaseEntity {
    int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    CartEntity cart;
}
