package com.fpt.edu.lifeform.Entity;

import com.fpt.edu.lifeform.Entity.Parent.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "promotion")
public class PromotionEntity extends BaseEntity {
    @Column(nullable = false, length = 255)
    String name;

    @Column(columnDefinition = "TEXT")
    String description;

    int discountRate;

    LocalDateTime startDate;
    LocalDateTime endDate;

    @Column(columnDefinition = "TEXT")
    String thumbnailUrl;

    @OneToMany(mappedBy = "promotion")
    Collection<ProductEntity> products;

    @OneToMany(mappedBy = "promotion")
    Collection<CategoryEntity> categories;
}
