package com.fmi.materials.repository;

import com.fmi.materials.model.Course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

//    @Query(value = "SET @Keywords = string_to_array('?1', ' ')\n" +
//        "SELECT * FROM courses\n" +
//        "WHERE (SELECT * )")
    Page<Course> findAll(Pageable pageable);
}
