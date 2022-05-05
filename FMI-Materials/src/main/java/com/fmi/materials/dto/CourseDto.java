package com.fmi.materials.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CourseDto {

    private Long id;
    private String name;
    private String description;
    private Long groupId;

    public CourseDto(Long id, String name, String description, Long groupId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.groupId = groupId;
    }
}
