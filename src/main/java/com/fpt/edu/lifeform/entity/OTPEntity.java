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
@Table(name = "otps")
public class OTPEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 10)
    String code;

    LocalDateTime expiredAt;

    LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

}
