package com.fpt.edu.lifeform.entity;

import com.fpt.edu.lifeform.entity.Parent.BaseEntity;
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
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "address_id")
    AddressEntity address;

    @ManyToMany
    Collection<PaymentEntity> payments;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    CouponEntity coupon;

    @OneToMany(mappedBy = "order")
    Collection<OrderDetailEntity> orderDetails;
}
