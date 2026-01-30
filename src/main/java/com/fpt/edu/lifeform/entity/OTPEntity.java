package com.fpt.edu.lifeform.entity;

import com.fpt.edu.lifeform.entity.Parent.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "otp")
public class OTPEntity extends BaseEntity {
    @Column(nullable = false, length = 10)
    String code;

    LocalDateTime expiredAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

}
