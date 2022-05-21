package com.fmi.materials.service;

import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.courselist.CourseListDto;
import com.fmi.materials.dto.courselist.CourseListDtoWithId;
import com.fmi.materials.model.CourseList;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CourseListService {
    CourseListDtoWithId createCourseList(CourseListDto courseListDto, Long userId);

    CourseListDtoWithId updateCourseList(Long userId, CourseListDtoWithId courseListDtoWithId);

    void deleteCourseList(Long userId, Long courseListId);

    CourseListDtoWithId getCourseList(Long courseListId, Long userId);

    List<CourseListDtoWithId> getAllCourseLists(Long userId);

    CourseListDtoWithId addCourseToList(Long courseId, Long courseListId, Long userId);

    void deleteCourseFromCourseList(Long userId, Long courseListId, Long courseId);
}
