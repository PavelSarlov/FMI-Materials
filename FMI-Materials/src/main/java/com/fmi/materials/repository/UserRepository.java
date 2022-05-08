package com.fmi.materials.repository;

import com.fmi.materials.model.CourseList;
import com.fmi.materials.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();

    User findByEmail(String email);

    @Query("SELECT users.id FROM users WHERE users.name = ?1 and users.email = ?2 and users.passwordHash = ?3")
    Long findByUserByNameAndEmailAndPassword(String name, String email, String password);

    @Query("SELECT cl FROM user_courses_lists cl WHERE cl.user_id = ?1")
    List<CourseList> findUserCourseLists(Long id);
}
