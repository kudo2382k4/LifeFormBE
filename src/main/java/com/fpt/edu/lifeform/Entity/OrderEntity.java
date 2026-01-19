package com.fpt.edu.lifeform.Entity;

import com.fpt.edu.lifeform.Entity.Parent.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "order")
public class OrderEntity extends BaseEntity {
    double totalPrice;

    @Column(length = 50)
    String status;

    @Column(unique = true, length = 50)
    String orderCode;

    @ManyToOne
    UserEntity user;

    @ManyToOne
    AddressEntity address;

    @ManyToMany
    Collection<PaymentEntity> payments;

    @ManyToOne
    CouponEntity coupon;

    @OneToMany(mappedBy = "order")
    Collection<OrderDetailEntity> orderDetails;
}
