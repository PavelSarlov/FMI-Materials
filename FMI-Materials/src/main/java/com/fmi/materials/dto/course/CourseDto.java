package com.fmi.materials.dto.course;

import com.fmi.materials.vo.CourseGroup;
import com.fmi.materials.vo.FacultyDepartment;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CourseDto {

    private String name;
    private String description;
    private FacultyDepartment facultyDepartment;
    private CourseGroup courseGroup;

    public CourseDto(String name, String description, FacultyDepartment facultyDepartment, CourseGroup courseGroup) {
        this.name = name;
        this.description = description;
        this.facultyDepartment = facultyDepartment;
        this.courseGroup = courseGroup;
    }
}
