package com.fmi.materials.service;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.section.SectionDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourseService {
    CourseDtoWithId createCourse(CourseDto course);

    void deleteCourse(Long courseId);

    CourseDtoWithId updateCourse(CourseDtoWithId course);

    CourseDtoWithId findById(Long courseId);

    List<CourseDtoWithId> findAllCourses();

    List<CourseDtoWithId> findAllCoursesByName(String name);

    List<SectionDto> findAllCourseSections(Long courseId);

    SectionDto createSection(SectionDto sectionDto, Long courseId);

    void deleteSection(Long sectionId);

    MaterialDto createMaterial(MultipartFile materialDto, Long sectionId) throws IOException;

    void deleteMaterial(Long materialId);
}
