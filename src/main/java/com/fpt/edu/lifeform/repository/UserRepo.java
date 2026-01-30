package com.fpt.edu.lifeform.repository;

import com.fpt.edu.lifeform.entity.UserEntity;
import com.fpt.edu.lifeform.repository.custom.JpaSpecificationRepository;

public interface UserRepo extends JpaSpecificationRepository<UserEntity, Long> {
}
