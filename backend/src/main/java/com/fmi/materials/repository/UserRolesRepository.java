package com.fmi.materials.repository;

import com.fmi.materials.model.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRolesRepository extends CrudRepository<UserRole, Long> {
    Optional<UserRole> findByName(String name);
}
