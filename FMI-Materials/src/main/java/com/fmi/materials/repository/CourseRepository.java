package com.fmi.materials.repository;

import java.util.List;

import com.fmi.materials.model.Course;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
    List<Course> findAll();

    // @Query(value =
    // "SELECT * FROM Courses co" +
    // "WHERE co.UserId = ?1"
    // )
    // List<Course> findByUser(User user);
}
