package com.fmi.materials.service;

import java.util.List;

import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;

public interface FacultyDepartmentService {

    FacultyDepartmentDto createFacultyDepartment(FacultyDepartmentDto facultyDepartmentDto);

    void deleteFacultyDepartment(Long facultyDepartmentId);

    FacultyDepartmentDto updateFacultyDepartment(FacultyDepartmentDto FacultyDepartmentDto);

    FacultyDepartmentDto findById(Long facultyDepartmentId);

    List<FacultyDepartmentDto> findAllFacultyDepartments();
}
