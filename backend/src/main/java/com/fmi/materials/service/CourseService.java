package com.fmi.materials.service;

import java.io.IOException;
import java.util.List;

import com.fmi.materials.dto.pagedresult.PagedResultDto;
import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.section.SectionDto;

import org.springframework.data.domain.Pageable;

public interface CourseService {
    CourseDto createCourse(CourseDto course);

    void deleteCourse(Long courseId);

    CourseDto updateCourse(CourseDtoWithId course);

    CourseDto findById(Long courseId);

    PagedResultDto<CourseDto> findCourses(String filter, String filterValue, Pageable pageable);

    List<SectionDto> findAllCourseSections(Long courseId);

    SectionDto createSection(SectionDto sectionDto, Long courseId);

    SectionDto findSectionById(Long sectionId);

    void deleteSection(Long sectionId);

    MaterialDto createMaterial(String fileFormat, String fileName, byte[] data, Long sectionId) throws IOException;

    void deleteMaterial(Long materialId);

    MaterialDtoWithData findMaterialById(Long materialId);

    MaterialDtoWithData findCourseMaterialByName(Long courseId, String name);
}
