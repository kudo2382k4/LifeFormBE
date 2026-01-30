package com.fpt.edu.lifeform.entity;

import com.fpt.edu.lifeform.entity.Parent.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "product_image")
public class ProductImageEntity extends BaseEntity {
    @Column(columnDefinition = "TEXT")
    String url;

    @ManyToOne
    @JoinColumn(name = "product_id")
    ProductEntity product;
}
