package com.fmi.materials.repository;

import java.util.List;

import com.fmi.materials.model.Course;

import org.springframework.data.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAll();

    // @Query(value =
    // "SELECT * FROM Courses co" +
    // "WHERE co.UserId = ?1"
    // )
    // List<Course> findByUser(User user);
}
