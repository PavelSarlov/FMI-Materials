package com.fmi.materials.dto.course;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.dto.section.SectionDto;
import com.fmi.materials.validator.SizeByteString;
import com.fmi.materials.vo.CourseGroup;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {

    @NotNull
    @SizeByteString(min = 4, max = 50, message = "Course name should be between 4 and 50 bytes.")
    private String name;
    @NotNull
    @SizeByteString(max = 255, message = "Course description should be maximum 255 bytes.")
    private String description;
    @NotNull
    @SizeByteString(min = 4, max = 50, message = "Creator name should be between 4 and 50 bytes.")
    private String createdBy;
    @NotNull
    private FacultyDepartmentDto facultyDepartmentDto;
    @NotNull
    private CourseGroup courseGroup;
    private List<SectionDto> sectionDtos;

    public CourseDto(String name, String description, String createdBy, FacultyDepartmentDto facultyDepartmentDto,
            CourseGroup courseGroup, List<SectionDto> sectionDtos) {
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.facultyDepartmentDto = facultyDepartmentDto;
        this.courseGroup = courseGroup;
        this.sectionDtos = sectionDtos;
    }
}
