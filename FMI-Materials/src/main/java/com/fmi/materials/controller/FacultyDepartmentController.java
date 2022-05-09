package com.fmi.materials.controller;

import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.service.FacultyDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/departments")
public class FacultyDepartmentController {
    @Autowired
    private FacultyDepartmentService facultyDepartmentService;

    @PostMapping
    public ResponseEntity createFacultyDepartment(@RequestBody FacultyDepartmentDto facultyDepartmentDto) {
        try {
            return new ResponseEntity(
                    this.facultyDepartmentService.createFacultyDepartment(facultyDepartmentDto),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    facultyDepartmentDto,
                    HttpStatus.CONFLICT
            );
        }
    }

    @GetMapping
    public ResponseEntity findAllFacultyDepartments() {
        return new ResponseEntity(
                this.facultyDepartmentService.findAllFacultyDepartments(),
                HttpStatus.OK
        );
    }

    @GetMapping("{id}")
    public ResponseEntity findFacultyDepartmentById(@PathVariable Long id) {
        try {
            return new ResponseEntity(
                    this.facultyDepartmentService.findById(id),
                    HttpStatus.FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFacultyDepartmentById(@PathVariable Long id) {
        try {
            this.facultyDepartmentService.deleteFacultyDepartment(id);

            return new ResponseEntity(
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PutMapping
    public ResponseEntity updateFacultyDepartment(@RequestBody FacultyDepartmentDto facultyDepartmentDto) {
        try {
            return new ResponseEntity(
                    this.facultyDepartmentService.updateFacultyDepartment(facultyDepartmentDto),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    facultyDepartmentDto,
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
