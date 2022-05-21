package com.fmi.materials.dto.courselist;

import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.user.UserDtoWithId;
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

    public CourseListDtoWithId(Long id, String listName, /*UserDtoWithId user,*/ List<CourseDtoWithId> courses) {
        super(listName, /*user,*/ courses);
        this.id = id;
    }
}
