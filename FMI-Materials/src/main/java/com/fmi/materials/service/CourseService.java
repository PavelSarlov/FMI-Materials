package com.fmi.materials.service;

import java.io.IOException;
import java.util.List;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.section.SectionDto;

import org.springframework.web.multipart.MultipartFile;

public interface CourseService {
    CourseDto createCourse(CourseDto course);

    void deleteCourse(Long courseId);

    CourseDto updateCourse(CourseDtoWithId course);

    CourseDto findById(Long courseId);

    List<CourseDto> findAllCourses();

    List<CourseDto> findAllCoursesByName(String name);

    List<SectionDto> findAllCourseSections(Long courseId);

    SectionDto createSection(SectionDto sectionDto, Long courseId);

    void deleteSection(Long sectionId);

    MaterialDto createMaterial(MultipartFile materialDto, Long sectionId) throws IOException;

    void deleteMaterial(Long materialId);
}
