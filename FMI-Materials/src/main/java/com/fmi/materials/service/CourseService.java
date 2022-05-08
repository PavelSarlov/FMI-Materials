package com.fmi.materials.service;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;

import java.util.List;

public interface CourseService {
    CourseDtoWithId createCourse(CourseDto course);

    void deleteCourse(Long courseId);

    CourseDtoWithId updateCourse(CourseDtoWithId course);

    CourseDtoWithId findById(Long courseId);

    List<CourseDtoWithId> findAllCourses();

    List<CourseDtoWithId> findAllCoursesByName(String name);
}
