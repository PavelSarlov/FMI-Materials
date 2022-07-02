package com.fmi.materials.dto.course;

import java.util.List;

import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.dto.section.SectionDto;
import com.fmi.materials.vo.CourseGroup;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class CourseDtoWithId extends CourseDto {
    private Long id;

    public CourseDtoWithId(Long id, String name, String description, String createdBy,
            FacultyDepartmentDto facultyDepartment, CourseGroup courseGroup, List<SectionDto> sectionDtos) {
        super(name, description, createdBy, facultyDepartment, courseGroup, sectionDtos);
        this.id = id;
    }
}
