package com.fpt.edu.lifeform.repository;

import com.fpt.edu.lifeform.entity.RoleEntity;
import com.fpt.edu.lifeform.repository.custom.JpaSpecificationRepository;
import com.fpt.edu.lifeform.utils.enums.RoleEnum;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaSpecificationRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleEnum roleName);
}
