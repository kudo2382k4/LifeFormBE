package com.fpt.edu.lifeform.Entity;

import com.fpt.edu.lifeform.Entity.Parent.BaseEntity;
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
@Table(name = "address")
public class AddressEntity extends BaseEntity {
    String street;

    @Column(length = 100)
    String city;

    @Column(length = 20)
    String phone;

    @ManyToOne
    UserEntity user;

    @OneToMany(mappedBy = "address")
    Collection<OrderEntity> orders;

}
