package com.fmi.materials.mapper;

import com.fmi.materials.dto.courselist.CourseListDto;
import com.fmi.materials.dto.courselist.CourseListDtoWithId;
import com.fmi.materials.model.CourseList;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseListDtoMapper {
    public CourseList convertToEntity(CourseListDto courseListDto) {
        return new CourseList(courseListDto.getListName());
    }

    public CourseList convertToEntityWithId(CourseListDtoWithId courseListDto) {
        return new CourseList(courseListDto.getId(), courseListDto.getListName());
    }

    public CourseListDto convertToDto(CourseList courseList) {
        return new CourseListDto(courseList.getListName());
    }

    public CourseListDtoWithId convertToDtoWithId(CourseList courseList) {
        return new CourseListDtoWithId(courseList.getId(), courseList.getListName());
    }

    public List<CourseListDtoWithId> convertToDtoList(List<CourseList> courseLists) {
        return courseLists.stream()
                .map(this::convertToDtoWithId)
                .collect(Collectors.toList());
    }
}
