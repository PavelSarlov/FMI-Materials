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
        if (courseDto == null) {
            return null;
        }

        return new Course(
                courseDto.getName(),
                courseDto.getDescription(),
                courseDto.getCreatedBy(),
                courseDto.getFacultyDepartmentDto() != null ? this.facultyDepartmentDtoMapper.convertToEntity(courseDto.getFacultyDepartmentDto()) : null,
                courseDto.getCourseGroup()
        );
    }

    public Course convertToEntityWithId(CourseDtoWithId courseDto) {
        if(courseDto == null) {
            return null;
        }

        return new Course(
                courseDto.getId(),
                courseDto.getName(),
                courseDto.getDescription(),
                courseDto.getCreatedBy(),
                courseDto.getFacultyDepartmentDto() != null ? this.facultyDepartmentDtoMapper.convertToEntity(courseDto.getFacultyDepartmentDto()) : null,
                courseDto.getCourseGroup()
        );
    }

    public CourseDto convertToDto(Course course) {
        if (course == null) {
            return null;
        }

        return new CourseDto(
                course.getName(),
                course.getDescription(),
                course.getCreatedBy(),
                course.getFacultyDepartment() != null ? this.facultyDepartmentDtoMapper.convertToDto(course.getFacultyDepartment()) : null,
                course.getCourseGroup(),
                course.getSections() == null ? course.getSections().stream()
                        .map(this.sectionDtoMapper::convertToDto)
                        .collect(Collectors.toList()) : null
        );
    }

    public CourseDtoWithId convertToDtoWithId(Course course) {
        if (course == null) {
            return null;
        }

        return new CourseDtoWithId(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getCreatedBy(),
                course.getFacultyDepartment() != null ? this.facultyDepartmentDtoMapper.convertToDto(course.getFacultyDepartment()) : null,
                course.getCourseGroup(),
                course.getSections() != null ? course.getSections().stream()
                        .map(this.sectionDtoMapper::convertToDto)
                        .collect(Collectors.toList()) : null
        );
    }
}