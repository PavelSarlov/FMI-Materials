package com.fmi.materials.controller;

import java.util.List;

import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.response.ResponseDtoSuccess;
import com.fmi.materials.service.FacultyDepartmentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/departments")
public class FacultyDepartmentController {

    private final FacultyDepartmentService facultyDepartmentService;

    @PostMapping
    public ResponseEntity<FacultyDepartmentDto> createFacultyDepartment(
            @RequestBody FacultyDepartmentDto facultyDepartmentDto) {
        return new ResponseEntity<FacultyDepartmentDto>(
                this.facultyDepartmentService.createFacultyDepartment(facultyDepartmentDto),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FacultyDepartmentDto>> findAllFacultyDepartments() {
        return new ResponseEntity<List<FacultyDepartmentDto>>(
                this.facultyDepartmentService.findAllFacultyDepartments(),
                HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<FacultyDepartmentDto> findFacultyDepartmentById(@PathVariable Long id) {
        return new ResponseEntity<FacultyDepartmentDto>(
                this.facultyDepartmentService.findById(id),
                HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseDto> deleteFacultyDepartmentById(@PathVariable Long id) {
        this.facultyDepartmentService.deleteFacultyDepartment(id);

        return new ResponseEntity<ResponseDto>(
                new ResponseDtoSuccess(HttpStatus.OK,
                        String.format("Faculty department with id = '%s' deleted successfully", id)),
                HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<FacultyDepartmentDto> updateFacultyDepartment(
            @RequestBody FacultyDepartmentDto facultyDepartmentDto) {
        return new ResponseEntity<FacultyDepartmentDto>(
                this.facultyDepartmentService.updateFacultyDepartment(facultyDepartmentDto),
                HttpStatus.OK);
    }
}
