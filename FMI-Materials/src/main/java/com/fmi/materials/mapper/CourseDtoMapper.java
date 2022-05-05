package com.fmi.materials.mapper;

import com.fmi.materials.dto.CourseDto;
import com.fmi.materials.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseDtoMapper {
    public CourseDto convertToDto(Course course) {
        return new CourseDto(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getGroupId()
        );
    }

    public Course convertToEntity(CourseDto courseDto) {
        if (courseDto.getId() != null) {
            return new Course(
                    courseDto.getId(),
                    courseDto.getName(),
                    courseDto.getDescription(),
                    courseDto.getGroupId()
            );
        }
        return new Course(
                courseDto.getName(),
                courseDto.getDescription(),
                courseDto.getGroupId()
        );
    }
}