package com.fmi.materials.service;

import com.fmi.materials.dto.course.CourseDtoWithId;

import java.util.List;

public interface FavouriteCoursesService {
    List<CourseDtoWithId> getFavouriteCourses(Long userId);

    void deleteFavouriteCourse(Long userId, Long courseId);

    List<CourseDtoWithId> addCourse(Long userId, Long courseId);
}
