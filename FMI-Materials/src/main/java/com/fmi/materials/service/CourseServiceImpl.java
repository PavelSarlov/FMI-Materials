package com.fmi.materials.service;

import com.fmi.materials.dto.CourseDto;
import com.fmi.materials.mapper.CourseDtoMapper;
import com.fmi.materials.model.Course;
import com.fmi.materials.repository.CourseRepository;
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
    public CourseDto createCourse(CourseDto courseDto) {
        if(this.courseRepository.existsById(courseDto.getId())) {
            throw new IllegalArgumentException(String.format(ALREADY_EXISTS_MESSAGE, courseDto.getId()));
        }
        Course course = this.courseDtoMapper.convertToEntity(courseDto);
        return this.courseDtoMapper.convertToDto(this.courseRepository.save(course));
    }

    @Override
    public void deleteCourse(Long courseId) {
        if(this.courseRepository.existsById(courseId)) {
            throw new NoSuchElementException(String.format(NOT_FOUND_MESSAGE, courseId));
        }
        this.courseRepository.deleteById(courseId);
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto) {
        if(this.courseRepository.existsById(courseDto.getId())) {
            throw new IllegalArgumentException(String.format(ALREADY_EXISTS_MESSAGE, courseDto.getId()));
        }
        Course course = this.courseDtoMapper.convertToEntity(courseDto);
        return this.courseDtoMapper.convertToDto(this.courseRepository.save(course));
    }

    @Override
    public CourseDto findById(Long courseId) {
        return this.courseDtoMapper.convertToDto(this.courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException(String.format(NOT_FOUND_MESSAGE, courseId))));
    }

    @Override
    public List<CourseDto> findAllCourses() {
        return this.courseRepository.findAll().stream()
                .map(this.courseDtoMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
