package com.fmi.materials.repository;

import java.util.List;
import java.util.Optional;

import com.fmi.materials.model.CourseList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseListRepository extends CrudRepository<CourseList, Long> {
    @Query(value = "SELECT * FROM user_courses_lists cl\n" +
            "WHERE cl.user_id = ?1 AND cl.list_name LIKE ?2", nativeQuery = true)
    Optional<CourseList> findByListName(Long userId, String name);

    @Query(value = "SELECT * FROM user_courses_lists cl\n" +
            "WHERE cl.user_id = ?1 AND cl.id = ?2", nativeQuery = true)
    Optional<CourseList> findUserCourseList(Long userId, Long courseListId);

    @Query(value = "SELECT c.course_id FROM user_courses_lists cl\n" +
            "JOIN courses_user_courses_lists c ON c.user_courses_list_id = cl.id\n" +
            "WHERE cl.user_id = ?1 AND cl.id = ?2 AND c.course_id = ?3", nativeQuery = true)
    Optional<Integer> findCourseInList(Long userId, Long courseListId, Long courseId);

    @Query(value = "SELECT * FROM user_courses_lists cl\n" +
            "WHERE cl.user_id = ?1", nativeQuery = true)
    List<CourseList> findUserCourseLists(Long id);
}
