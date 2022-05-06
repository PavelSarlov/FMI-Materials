package com.fmi.materials.service;

import com.fmi.materials.dto.CourseDto;

import java.util.List;

public interface CourseService {
    CourseDto createCourse(CourseDto course);

    void deleteCourse(Long courseId);

    CourseDto updateCourse(CourseDto course);

    CourseDto findById(Long courseId);

    List<CourseDto> findAllCourses();

}
