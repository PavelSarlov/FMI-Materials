package com.fmi.materials.mapper;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseDtoMapper {

    public Course toEntity(CourseDto courseDto) {
        return new Course(
                courseDto.getName(),
                courseDto.getDescription(),
                courseDto.getFacultyDepartment(),
                courseDto.getCourseGroup()
        );
    }

    public Course toEntityWithId(CourseDtoWithId courseDto) {
        return new Course(
                courseDto.getId(),
                courseDto.getName(),
                courseDto.getDescription(),
                courseDto.getFacultyDepartment(),
                courseDto.getCourseGroup()
        );
    }

    public CourseDto toDto(Course course) {
        return new CourseDto(
                course.getName(),
                course.getDescription(),
                course.getFacultyDepartment(),
                course.getCourseGroup()
        );
    }

    public CourseDto toDtoWithId(Course course) {
        return new CourseDtoWithId(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getFacultyDepartment(),
                course.getCourseGroup()
        );
    }
}