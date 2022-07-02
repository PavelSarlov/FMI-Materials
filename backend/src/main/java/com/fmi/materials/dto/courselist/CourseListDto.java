package com.fmi.materials.dto.courselist;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.validator.SizeByteString;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseListDto {

    @NotNull
    @SizeByteString(min = 1, max = 50, message = "List name should be between 1 and 50 bytes.")
    private String listName;
    private List<CourseDtoWithId> courses;

    public CourseListDto() {}

    public CourseListDto(String listName, List<CourseDtoWithId> courses) {
        this.listName = listName;
        this.courses = courses;
    }
}
