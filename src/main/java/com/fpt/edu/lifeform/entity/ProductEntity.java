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
@Table(name = "products")
public class ProductEntity extends BaseEntity {

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    double price;

    @Column(columnDefinition = "TEXT")
    String description;

    @OneToMany
    Collection<OrderDetailEntity> orderDetails;

    @OneToMany(mappedBy = "product")
    Collection<ProductImageEntity> productImages;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    BrandEntity brand;

    @ManyToMany
    Collection<TargetEntity> targets;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    PromotionEntity promotion;

    @ManyToOne
    @JoinColumn(name = "category_id")
    CategoryEntity category;

    @ManyToMany
    Collection<SizeEntity> sizes;

    @ManyToMany
    Collection<ColorEntity> colors;

    @OneToMany(mappedBy = "product")
    Collection<CartDetailEntity> cartDetails;

    @OneToMany(mappedBy = "product")
    Collection<FeedbackEntity> feedbackEntities;
}
