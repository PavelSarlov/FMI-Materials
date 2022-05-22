package com.fmi.materials.repository;

import com.fmi.materials.model.Course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {

//    @Query(value = "SELECT * FROM courses\n" +
//            "WHERE ?1 ILIKE ALL(string_to_array(?2, ','))", nativeQuery = true)
//    Page<Course> findAll(Specification<Course> spec, Pageable pageable);
}
