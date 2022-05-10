package com.fmi.materials.service;

import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;

import java.util.List;

public interface FacultyDepartmentService {

    FacultyDepartmentDto createFacultyDepartment(FacultyDepartmentDto facultyDepartmentDto);

    void deleteFacultyDepartment(Long facultyDepartmentId);

    FacultyDepartmentDto updateFacultyDepartment(FacultyDepartmentDto FacultyDepartmentDto);

    FacultyDepartmentDto findById(Long facultyDepartmentId);

    List<FacultyDepartmentDto> findAllFacultyDepartments();
}
