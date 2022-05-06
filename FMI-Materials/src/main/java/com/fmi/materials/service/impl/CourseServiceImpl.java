package com.fmi.materials.service.impl;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.mapper.CourseDtoMapper;
import com.fmi.materials.model.Course;
import com.fmi.materials.repository.CourseRepository;
import com.fmi.materials.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private final String ALREADY_EXISTS_MESSAGE = "Course with ID = '%s' already exists";
    private final String NOT_FOUND_MESSAGE = "Course with ID = '%s' not found";

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseDtoMapper courseDtoMapper;

    @Override
    public CourseDtoWithId createCourse(CourseDto courseDto) {
        Course course = this.courseDtoMapper.toEntity(courseDto);
        return this.courseDtoMapper.toDtoWithId(this.courseRepository.save(course));
    }

    @Override
    public void deleteCourse(Long courseId) {
        if(this.courseRepository.existsById(courseId)) {
            throw new NoSuchElementException(String.format(NOT_FOUND_MESSAGE, courseId));
        }
        this.courseRepository.deleteById(courseId);
    }

    @Override
    public CourseDtoWithId updateCourse(CourseDtoWithId courseDto) {
        if(!this.courseRepository.existsById(courseDto.getId())) {
            throw new NoSuchElementException(String.format(NOT_FOUND_MESSAGE, courseDto.getId()));
        }
        Course course = this.courseDtoMapper.toEntityWithId(courseDto);
        return this.courseDtoMapper.toDtoWithId(this.courseRepository.save(course));
    }

    @Override
    public CourseDtoWithId findById(Long courseId) {
        return this.courseDtoMapper.toDtoWithId(this.courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException(String.format(NOT_FOUND_MESSAGE, courseId))));
    }

    @Override
    public List<CourseDtoWithId> findAllCourses() {
        return this.courseRepository.findAll().stream()
                .map(this.courseDtoMapper::toDtoWithId)
                .collect(Collectors.toList());
    }
}
