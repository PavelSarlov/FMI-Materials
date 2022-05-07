package com.fmi.materials.mapper;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CourseDtoMapper {

    @Autowired
    private FacultyDepartmentDtoMapper facultyDepartmentDtoMapper;
    @Autowired
    private SectionDtoMapper sectionDtoMapper;

    public Course convertToEntity(CourseDto courseDto) {
        return new Course(
                courseDto.getName(),
                courseDto.getDescription(),
                courseDto.getCreatedBy(),
                null,
                courseDto.getCourseGroup(),
                null
        );
    }

    public Course convertToEntityWithId(CourseDtoWithId courseDto) {
        return new Course(
                courseDto.getId(),
                courseDto.getName(),
                courseDto.getDescription(),
                courseDto.getCreatedBy(),
                null,
                courseDto.getCourseGroup(),
                null
        );
    }

    public CourseDto convertToDto(Course course) {
        return new CourseDto(
                course.getName(),
                course.getDescription(),
                course.getCreatedBy(),
                this.facultyDepartmentDtoMapper.convertToDto(course.getFacultyDepartment()),
                course.getCourseGroup(),
                course.getSections().stream()
                        .map(this.sectionDtoMapper::convertToDto)
                        .collect(Collectors.toList())
        );
    }

    public CourseDtoWithId convertToDtoWithId(Course course) {
        return new CourseDtoWithId(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getCreatedBy(),
                this.facultyDepartmentDtoMapper.convertToDto(course.getFacultyDepartment()),
                course.getCourseGroup(),
                course.getSections().stream()
                        .map(this.sectionDtoMapper::convertToDto)
                        .collect(Collectors.toList())
        );
    }
}