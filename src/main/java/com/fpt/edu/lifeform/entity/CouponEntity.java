package com.fpt.edu.lifeform.entity;

import com.fpt.edu.lifeform.entity.Parent.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "coupon")
public class CouponEntity extends BaseEntity {
    @Column(nullable = false, unique = true, length = 50)
    String code;

    @Column(nullable = false)
    int discountPercent;

    @OneToMany
    Collection<OrderEntity> orders;
}
