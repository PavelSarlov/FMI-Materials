package com.fmi.materials.dto.facultydepartment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fmi.materials.validator.SizeByteString;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacultyDepartmentDto {

    private Long id;

    @NotNull
    @SizeByteString(min=4, max=50, message = "Department name should be between 4 and 50 bytes.")
    private String name;

    public FacultyDepartmentDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
