package com.fmi.materials.dto.course.courselist;

import com.fmi.materials.dto.course.CourseDtoWithId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CourseListDto {
    private String listName;
    private List<CourseDtoWithId> courses;

    public CourseListDto() {}

    public CourseListDto(String listName, List<CourseDtoWithId> courses) {
        this.listName = listName;
        this.courses = courses;
    }
}
