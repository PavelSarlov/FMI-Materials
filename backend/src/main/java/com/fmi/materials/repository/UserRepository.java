package com.fmi.materials.repository;

import com.fmi.materials.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT users.id FROM users " +
            "WHERE users.name = ?1 and users.email = ?2 and users.passwordHash = ?3", nativeQuery = true)
    Long findUserByNameAndEmailAndPassword(String name, String email, String password);
}
