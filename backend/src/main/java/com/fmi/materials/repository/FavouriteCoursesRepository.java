package com.fmi.materials.repository;

import java.util.Optional;

import com.fmi.materials.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FavouriteCoursesRepository extends CrudRepository<User, Long> {
    @Query(value = "SELECT fc.course_id FROM user_favourite_courses fc\n" +
            "WHERE fc.user_id = ?1 AND fc.course_id = ?2", nativeQuery = true)
    Optional<Integer> findCourseInFavourites(Long userId, Long courseId);
}
