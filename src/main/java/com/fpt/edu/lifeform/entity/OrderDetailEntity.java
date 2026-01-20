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
@Table(name = "order_detail")
public class OrderDetailEntity extends BaseEntity {
    double price;
    int quantity;

    @ManyToOne
    OrderEntity order;

    @ManyToOne
    ProductEntity product;

    @ManyToOne
    SizeEntity size;

    @ManyToOne
    ColorEntity color;
}
