package com.fmi.materials.dto.facultydepartment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class FacultyDepartmentDto {

    private Long id;
    private String name;

    public FacultyDepartmentDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
