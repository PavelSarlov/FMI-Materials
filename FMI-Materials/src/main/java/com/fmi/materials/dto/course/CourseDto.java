package com.fmi.materials.dto.course;

import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.dto.section.SectionDto;
import com.fmi.materials.vo.CourseGroup;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CourseDto {

    private String name;
    private String description;
    private String createdBy;
    private FacultyDepartmentDto facultyDepartmentDto;
    private CourseGroup courseGroup;
    private List<SectionDto> sectionDtos;

    public CourseDto(String name, String description, String createdBy, FacultyDepartmentDto facultyDepartmentDto, CourseGroup courseGroup, List<SectionDto> sectionDtos) {
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.facultyDepartmentDto = facultyDepartmentDto;
        this.courseGroup = courseGroup;
        this.sectionDtos = sectionDtos;
    }
}
