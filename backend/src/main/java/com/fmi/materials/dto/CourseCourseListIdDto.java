package com.fmi.materials.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CourseCourseListIdDto {
    private Long courseId;
    private Long courseListId;

    public CourseCourseListIdDto(Long courseId, Long courseListId) {
        this.courseId = courseId;
        this.courseListId = courseListId;
    }
}
