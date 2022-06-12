package com.fmi.materials.dto.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.dto.section.SectionDto;
import com.fmi.materials.vo.CourseGroup;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {

    @NotNull
    @Size(min=4, max = 50, message = "Course name should be between 4 and 50 characters.")
    private String name;
    @NotNull
    @Size(max = 255, message = "Course description should be maximum 255 characters.")
    private String description;
    @NotNull
    @Size(min=4, max = 50, message = "Creator name should be between 4 and 50 characters.")
    private String createdBy;
    @NotNull
    private FacultyDepartmentDto facultyDepartmentDto;
    @NotNull
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
