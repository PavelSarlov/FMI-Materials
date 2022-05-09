package com.fmi.materials.repository;

import com.fmi.materials.model.CourseList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseListRepository extends CrudRepository<CourseList, Long> {
    CourseList findByListName(String name);

    @Query(value = "SELECT * FROM user_courses_lists cl WHERE cl.user_id = ?1 AND cl.id = ?2", nativeQuery = true)
    Optional<CourseList> findUserCourseList(Long userId, Long courseId);

    @Query(value = "SELECT * FROM user_courses_lists cl WHERE cl.user_id = ?1", nativeQuery = true)
    List<CourseList> findUserCourseLists(Long id);
}
