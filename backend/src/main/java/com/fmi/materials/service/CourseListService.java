package com.fmi.materials.service;

import java.util.List;

import com.fmi.materials.dto.courselist.CourseListDto;
import com.fmi.materials.dto.courselist.CourseListDtoWithId;

public interface CourseListService {
    CourseListDtoWithId createCourseList(CourseListDto courseListDto, Long userId);

    CourseListDtoWithId updateCourseList(Long userId, CourseListDtoWithId courseListDtoWithId);

    CourseListDtoWithId changeCourseListName(Long userId, Long courseListId, String courseListName);

    void deleteCourseList(Long userId, Long courseListId);

    CourseListDtoWithId getCourseList(Long courseListId, Long userId);

    List<CourseListDtoWithId> getAllCourseLists(Long userId);

    CourseListDtoWithId addCourseToList(Long courseId, Long courseListId, Long userId);

    void deleteCourseFromCourseList(Long userId, Long courseListId, Long courseId);
}
