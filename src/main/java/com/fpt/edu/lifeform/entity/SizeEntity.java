package com.fpt.edu.lifeform.entity;

import com.fpt.edu.lifeform.entity.Parent.BaseEntity;
import com.fpt.edu.lifeform.Util.Enum.SizeEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "size")
public class SizeEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    @Enumerated(EnumType.STRING)
    SizeEnum name;

    @OneToMany(mappedBy = "size")
    Collection<OrderDetailEntity> orderDetails;

    @ManyToMany
    Collection<ProductEntity> products;
}
