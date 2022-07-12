package com.fmi.materials.service;

import java.util.List;

import com.fmi.materials.dto.course.CourseDtoWithId;

public interface FavouriteCoursesService {
    List<CourseDtoWithId> getFavouriteCourses(Long userId);

    void deleteFavouriteCourse(Long userId, Long courseId);

    List<CourseDtoWithId> addCourse(Long userId, Long courseId);
}
