package com.fmi.materials.service;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.pagedresult.PagedResultDto;
import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.section.SectionDto;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface CourseService {
    CourseDto createCourse(CourseDto course);

    ResponseDto deleteCourse(Long courseId);

    CourseDto updateCourse(CourseDtoWithId course);

    CourseDto findById(Long courseId);

    PagedResultDto<CourseDto> findCourses(String filter, String filterValue, Pageable pageable);

    List<SectionDto> findAllCourseSections(Long courseId);

    SectionDto createSection(SectionDto sectionDto, Long courseId);

    SectionDto findSectionById(Long sectionId);

    ResponseDto deleteSection(Long sectionId);

    SectionDto patchSection(SectionDto sectionDto) throws IllegalAccessException;

    MaterialDto createMaterial(MaterialDto materialDto, Long sectionid) throws IOException;

    ResponseDto deleteMaterial(Long materialId);

    MaterialDtoWithData findMaterialById(Long materialId);

    MaterialDtoWithData findCourseMaterialByName(Long courseId, String name);
}
