package com.fpt.edu.lifeform.repository;

import com.fpt.edu.lifeform.entity.UserEntity;
import com.fpt.edu.lifeform.repository.custom.JpaSpecificationRepository;
import com.fpt.edu.lifeform.utils.enums.AccountTypeEnum;

import java.util.Optional;

public interface UserRepo extends JpaSpecificationRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailAndAccountType(String email, AccountTypeEnum accountType);
    Optional<UserEntity> findByEmail(String email);

}
