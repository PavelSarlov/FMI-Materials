package com.fmi.materials.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.fmi.materials.model.Course;
import com.fmi.materials.repository.CourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    private final String ALREADY_EXISTS = "Course with ID = '%s' already exists";
    private final String NOT_FOUND_MESSAGE = "Course with ID = '%s' not found";

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course createCourse(Course course) {
        if(this.courseRepository.existsById(course.getId())) {
            throw new IllegalArgumentException(String.format(ALREADY_EXISTS, course.getId()));
        }
        return this.courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long courseId) {
        if(this.courseRepository.existsById(courseId)) {
            throw new IllegalArgumentException(String.format(ALREADY_EXISTS, courseId));
        }
        this.courseRepository.deleteById(courseId);
    }

    @Override
    public Course updateCourse(Course course) {
        if(this.courseRepository.existsById(course.getId())) {
            throw new IllegalArgumentException(String.format(ALREADY_EXISTS, course.getId()));
        }
        return this.courseRepository.save(course);
    }

    @Override
    public Course findById(Long courseId) {
        return this.courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException(String.format(NOT_FOUND_MESSAGE, courseId)));
    }

    @Override
    public List<Course> findAllCourses() {
        return this.courseRepository.findAll();
    }
}
