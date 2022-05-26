package com.fmi.materials.mapper;

import com.fmi.materials.dto.course.courselist.CourseListDto;
import com.fmi.materials.dto.course.courselist.CourseListDtoWithId;
import com.fmi.materials.model.CourseList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseListDtoMapper {
    @Autowired
    private CourseDtoMapper courseDtoMapper;

    public CourseList convertToEntity(CourseListDto courseListDto) {
        return new CourseList(
                courseListDto.getListName(),
                courseListDto.getCourses() != null ? courseListDto.getCourses().stream().map(this.courseDtoMapper::convertToEntityWithId).collect(Collectors.toSet()) : null
        );
    }

    public CourseList convertToEntityWithId(CourseListDtoWithId courseListDto) {
        return new CourseList(
                courseListDto.getId(), courseListDto.getListName(),
                courseListDto.getCourses() != null ? courseListDto.getCourses().stream().map(this.courseDtoMapper::convertToEntityWithId).collect(Collectors.toSet()) : null
        );
    }

    public CourseListDto convertToDto(CourseList courseList) {

        return new CourseListDto(
                courseList.getListName(),
                courseList.getCourses() != null ? courseList.getCourses().stream()
                        .map(this.courseDtoMapper::convertToDtoWithId)
                        .collect(Collectors.toList()) : null
        );
    }

    public CourseListDtoWithId convertToDtoWithId(CourseList courseList) {
        return new CourseListDtoWithId(
                courseList.getId(),
                courseList.getListName(),
                courseList.getCourses() != null ? courseList.getCourses().stream()
                        .map(this.courseDtoMapper::convertToDtoWithId)
                        .collect(Collectors.toList()) : null
        );
    }

    public List<CourseListDtoWithId> convertToDtoList(List<CourseList> courseLists) {
        return courseLists.stream()
                .map(this::convertToDtoWithId)
                .collect(Collectors.toList());
    }
}
