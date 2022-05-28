package com.fmi.materials.dto.facultydepartment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacultyDepartmentDto {

    private Long id;
    private String name;

    public FacultyDepartmentDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
