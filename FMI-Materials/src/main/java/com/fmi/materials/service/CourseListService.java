package com.fmi.materials.service;

import com.fmi.materials.dto.courselist.CourseListDto;
import com.fmi.materials.dto.courselist.CourseListDtoWithId;
import com.fmi.materials.model.CourseList;

import java.util.List;

public interface CourseListService {
    CourseListDtoWithId createCourseList(CourseListDto courseListDto, Long userId);

    CourseListDtoWithId updateCourseList(CourseListDtoWithId courseListDtoWithId);

    CourseListDtoWithId getCourseList(Long courseListId, Long userId);

    List<CourseListDtoWithId> getAllCourseLists(Long userId);

    CourseListDto addCourseToList(Long courseId, Long courseListId, Long userId);
}
