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

    private String name;

    public FacultyDepartmentDto(String name) {
        this.name = name;
    }
}
