package com.fmi.materials.dto.courselist;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CourseListDtoWithId extends CourseListDto {
    private Long id;

    public CourseListDtoWithId(Long id, String listName) {
        super(listName);
        this.id = id;
    }
}
