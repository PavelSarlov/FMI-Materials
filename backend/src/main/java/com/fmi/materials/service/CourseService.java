package com.fmi.materials.service;

import java.util.List;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.pagedresult.PagedResultDto;
import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.section.SectionDto;

import org.springframework.data.domain.Pageable;

public interface CourseService {
    CourseDto createCourse(CourseDto course);

    ResponseDto deleteCourse(Long courseId);

    CourseDto updateCourse(CourseDtoWithId course);

    CourseDto findById(Long courseId);

    PagedResultDto<CourseDto> findCourses(String filter, String filterValue, Pageable pageable);

    List<SectionDto> findAllCourseSections(Long courseId);

}
