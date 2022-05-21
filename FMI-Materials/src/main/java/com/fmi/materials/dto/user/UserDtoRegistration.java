package com.fmi.materials.dto.user;

import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.courselist.CourseListDtoWithId;
import com.fmi.materials.model.UserRole;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class UserDtoRegistration extends UserDto {
    private String repeatedPassword;

    public UserDtoRegistration(String repeatedPassword, String name, String password, String email, List<CourseListDtoWithId> courseLists, List<CourseDtoWithId> favouriteCourses) {
        super(name, password, email, courseLists, favouriteCourses);
        this.repeatedPassword = repeatedPassword;
    }
}
