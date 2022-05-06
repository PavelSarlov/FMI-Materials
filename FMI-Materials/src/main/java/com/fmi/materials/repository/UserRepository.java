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

    @Query("SELECT * FROM UserCoursesLists INNER JOIN Users ON UserCoursesLists.user_id = Users.id WHERE UserCoursesLists.id = ?1")
    CourseList findCourseListById(Long id);

    @Query("SELECT * FROM UserCoursesLists INNER JOIN Users ON UserCoursesLists.user_id = Users.id")
    List<CourseList> findAllUserCoursesLists();
}
