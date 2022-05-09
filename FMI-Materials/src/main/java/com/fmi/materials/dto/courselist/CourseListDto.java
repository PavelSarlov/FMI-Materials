package com.fmi.materials.dto.courselist;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CourseListDto {
    private String listName;

    public CourseListDto() {
    }

    public CourseListDto(String listName) {
        this.listName = listName;
    }
}
