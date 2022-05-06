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
public class CourseDtoWithId extends CourseDto {
    private Long id;

    public CourseDtoWithId(Long id, String name, String description, FacultyDepartment facultyDepartment, CourseGroup courseGroup) {
        super(name, description, facultyDepartment, courseGroup);
    }
}
