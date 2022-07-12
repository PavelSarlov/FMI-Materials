package com.fmi.materials.mapper;

import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.model.FacultyDepartment;

import org.springframework.stereotype.Component;

@Component
public class FacultyDepartmentDtoMapper {

    public FacultyDepartment convertToEntity(FacultyDepartmentDto facultyDepartmentDto) {
        if (facultyDepartmentDto == null) {
            return null;
        }

        return new FacultyDepartment(
                facultyDepartmentDto.getId(),
                facultyDepartmentDto.getName()
        );
    }

    public FacultyDepartmentDto convertToDto(FacultyDepartment facultyDepartment) {
        if (facultyDepartment == null) {
            return null;
        }

        return new FacultyDepartmentDto(
                facultyDepartment.getId(),
                facultyDepartment.getName()
        );
    }
}
