package com.fmi.materials.dto.courselist;

import java.util.List;

import com.fmi.materials.dto.course.CourseDtoWithId;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class CourseListDtoWithId extends CourseListDto {
    private Long id;

    public CourseListDtoWithId(Long id, String listName, List<CourseDtoWithId> courses) {
        super(listName, courses);
        this.id = id;
    }
}
