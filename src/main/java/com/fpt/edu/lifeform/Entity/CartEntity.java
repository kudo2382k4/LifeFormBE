package com.fpt.edu.lifeform.Entity;

import com.fpt.edu.lifeform.Entity.Parent.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "cart")
public class CartEntity extends BaseEntity {
    @OneToOne
    UserEntity user;

    @OneToMany(mappedBy = "cart")
    Collection<CartDetailEntity> cartDetails;
}
