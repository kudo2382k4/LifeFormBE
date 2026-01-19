package com.fpt.edu.lifeform.Entity;

import com.fpt.edu.lifeform.Entity.Parent.BaseEntity;
import com.fpt.edu.lifeform.Util.Enum.AccountTypeEnum;
import com.fpt.edu.lifeform.Util.Enum.GenderEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user")
public class UserEntity extends BaseEntity {

    @Column(nullable = false)
    String fullname;

    @Column(nullable = false, unique = true)
    String email;

    String phone;

    @Column(columnDefinition = "TEXT")
    String avatar;

    @Column(nullable = false)
    String password;

    @Column(columnDefinition = "TEXT")
    String refreshToken;

    @Enumerated(EnumType.STRING)
    GenderEnum gender;

    LocalDate dob;

    Boolean active;

    @Enumerated(EnumType.STRING)
    AccountTypeEnum accountType;

    @OneToMany(mappedBy = "user")
    Collection<UserNotificationEntity> userNotificationEntities;

    @OneToMany
    Collection<AddressEntity> addressEntities;

    @OneToOne
    OTPEntity otpEntity;

    @ManyToOne
    RoleEntity role;

    @OneToMany(mappedBy = "user")
    Collection<OrderEntity> orderEntities;

    @OneToOne
    CartEntity cart;

    @OneToMany(mappedBy = "user")
    Collection<FeedbackEntity> feedbackEntities;

    @Override
    public void initDefault() {
        active = false;
    }
}
