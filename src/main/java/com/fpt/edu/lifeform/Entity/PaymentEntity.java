package com.fpt.edu.lifeform.Entity;

import com.fpt.edu.lifeform.Entity.Parent.BaseEntity;
import com.fpt.edu.lifeform.Util.Enum.PayMethodEnum;
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
@Table(name = "payment")
public class PaymentEntity extends BaseEntity {
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    PayMethodEnum name;

    @ManyToMany
    Collection<OrderEntity> orders;
}
