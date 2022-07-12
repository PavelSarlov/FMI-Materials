package com.fmi.materials.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.fmi.materials.dto.courselist.CourseListDto;
import com.fmi.materials.dto.courselist.CourseListDtoWithId;
import com.fmi.materials.model.CourseList;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CourseListDtoMapper {

    private final CourseDtoMapper courseDtoMapper;

    public CourseList convertToEntity(CourseListDto courseListDto) {
        if (courseListDto == null) {
            return null;
        }

        return new CourseList(
                courseListDto.getListName(),
                courseListDto.getCourses() != null ? courseListDto.getCourses().stream()
                        .map(this.courseDtoMapper::convertToEntityWithId).collect(Collectors.toSet()) : null);
    }

    public CourseList convertToEntityWithId(CourseListDtoWithId courseListDto) {
        if (courseListDto == null) {
            return null;
        }

        return new CourseList(
                courseListDto.getId(), courseListDto.getListName(),
                courseListDto.getCourses() != null ? courseListDto.getCourses().stream()
                        .map(this.courseDtoMapper::convertToEntityWithId).collect(Collectors.toSet()) : null);
    }

    public CourseListDto convertToDto(CourseList courseList) {
        if (courseList == null) {
            return null;
        }

        return new CourseListDto(
                courseList.getListName(),
                courseList.getCourses() != null ? courseList.getCourses().stream()
                        .map(this.courseDtoMapper::convertToDtoWithId)
                        .collect(Collectors.toList()) : null);
    }

    public CourseListDtoWithId convertToDtoWithId(CourseList courseList) {
        if (courseList == null) {
            return null;
        }

        return new CourseListDtoWithId(
                courseList.getId(),
                courseList.getListName(),
                courseList.getCourses() != null ? courseList.getCourses().stream()
                        .map(this.courseDtoMapper::convertToDtoWithId)
                        .collect(Collectors.toList()) : null);
    }

    public List<CourseListDtoWithId> convertToDtoList(List<CourseList> courseLists) {
        if (courseLists == null) {
            return null;
        }

        return courseLists.stream()
                .filter(c -> c != null)
                .map(this::convertToDtoWithId)
                .collect(Collectors.toList());
    }
}
