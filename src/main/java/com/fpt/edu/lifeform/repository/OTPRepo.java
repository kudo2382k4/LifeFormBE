package com.fpt.edu.lifeform.repository;

import com.fpt.edu.lifeform.entity.OTPEntity;
import com.fpt.edu.lifeform.repository.custom.JpaSpecificationRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Repository
public interface OTPRepo extends JpaSpecificationRepository<OTPEntity, Long> {
    Optional<OTPEntity> findByCode(String code);

    @Query("SELECT o FROM OTPEntity o WHERE o.expiredAt < :currentTime")
    Set<OTPEntity> findAllExpiredOTP(@Param("currentTime") Instant currentTime);
}