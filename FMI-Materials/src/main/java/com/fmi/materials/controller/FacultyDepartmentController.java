package com.fmi.materials.controller;

import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.response.ResponseDtoSuccess;
import com.fmi.materials.service.FacultyDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/departments")
public class FacultyDepartmentController {
    @Autowired
    private FacultyDepartmentService facultyDepartmentService;

    @PostMapping
    public ResponseEntity<FacultyDepartmentDto> createFacultyDepartment(@RequestBody FacultyDepartmentDto facultyDepartmentDto) {
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
                HttpStatus.FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseDto> deleteFacultyDepartmentById(@PathVariable Long id) {
        this.facultyDepartmentService.deleteFacultyDepartment(id);

        return new ResponseEntity<ResponseDto>(
                new ResponseDtoSuccess(HttpStatus.OK, String.format("Faculty department with id = '%s' deleted successfully", id)),
                HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<FacultyDepartmentDto> updateFacultyDepartment(@RequestBody FacultyDepartmentDto facultyDepartmentDto) {
        return new ResponseEntity<FacultyDepartmentDto>(
                this.facultyDepartmentService.updateFacultyDepartment(facultyDepartmentDto),
                HttpStatus.OK);
    }
}
