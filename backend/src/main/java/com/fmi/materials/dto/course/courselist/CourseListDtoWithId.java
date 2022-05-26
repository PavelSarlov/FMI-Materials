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
public class CourseListDtoWithId extends CourseListDto {
    private Long id;

    public CourseListDtoWithId(Long id, String listName, List<CourseDtoWithId> courses) {
        super(listName, courses);
        this.id = id;
    }
}
