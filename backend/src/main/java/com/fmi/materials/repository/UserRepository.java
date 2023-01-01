package com.fmi.materials.repository;

import java.util.List;
import java.util.Optional;

import com.fmi.materials.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();

    Optional<User> findByEmail(String email);


    @Query(value = "SELECT * FROM users u\n" +
            "JOIN users_user_roles uur ON u.id = uur.user_id\n" +
            "JOIN user_roles ur ON ur.id = uur.role_id\n" +
            "WHERE ur.name = 'ADMIN'", nativeQuery = true)
    List<User> findAllAdmins();


    @Query(value = "SELECT users.id FROM users\n" +
            "WHERE users.name = ?1 and users.email = ?2 and users.passwordHash = ?3", nativeQuery = true)
    Long findUserByNameAndEmailAndPassword(String name, String email, String password);

    Optional<User> findByName(String name);
}
