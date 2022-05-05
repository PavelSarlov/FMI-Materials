package com.fmi.materials.service;

import java.util.List;

import com.fmi.materials.model.Course;

public interface CourseService {
    Course createCourse(Course course);

    void deleteCourse(Long courseId);

    Course updateCourse(Course course);

    Course findById(Long courseId);

    List<Course> findAllCourses();
}
