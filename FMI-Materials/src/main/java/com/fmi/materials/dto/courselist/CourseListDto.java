package com.fmi.materials.dto.courselist;

import com.fmi.materials.dto.course.CourseDto;
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
public class CourseListDto {
    private String listName;
    private UserDtoWithId user;
    private List<CourseDtoWithId> courses;

    public CourseListDto() {
    }

    public CourseListDto(String listName, UserDtoWithId user, List<CourseDtoWithId> courses) {
        this.listName = listName;
        this.user = user;
        this.courses = courses;
    }
}
