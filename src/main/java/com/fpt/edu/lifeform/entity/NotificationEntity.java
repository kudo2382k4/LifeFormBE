package com.fpt.edu.lifeform.entity;

import com.fpt.edu.lifeform.entity.Parent.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "notification")
public class NotificationEntity extends BaseEntity {
    @Column(nullable = false, length = 255)
    String title;

    @Column(columnDefinition = "TEXT")
    String content;

    LocalDateTime setDate;

    @OneToMany(mappedBy = "notification")
    Collection<UserNotificationEntity> userNotificationEntities;

    @Override
    public void initDefault() {
        if(setDate == null){
            setDate = LocalDateTime.now();
        }
    }
}
