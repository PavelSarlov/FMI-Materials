package com.fmi.materials.dto.user;

import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.courselist.CourseListDtoWithId;
import com.fmi.materials.model.UserRole;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class UserDtoWithId extends UserDto{
    private Long id;

    public UserDtoWithId(Long id, String name, String password, String email, List<CourseListDtoWithId> courseLists, List<CourseDtoWithId> favouriteCourses) {
        super(name, password, email, courseLists, favouriteCourses);
        this.id = id;
    }
}
