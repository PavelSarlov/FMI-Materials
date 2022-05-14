package com.fmi.materials.controller;

import java.util.List;

import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.service.FacultyDepartmentService;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping(value = "api/departments")
public class FacultyDepartmentController {
    @Autowired
    private FacultyDepartmentService facultyDepartmentService;

    @PostMapping
    public ResponseEntity<Object> createFacultyDepartment(@RequestBody FacultyDepartmentDto facultyDepartmentDto) {
        return new ResponseEntity<Object>(
                this.facultyDepartmentService.createFacultyDepartment(facultyDepartmentDto),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> findAllFacultyDepartments() {
        return new ResponseEntity<Object>(
                this.facultyDepartmentService.findAllFacultyDepartments(),
                HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findFacultyDepartmentById(@PathVariable Long id) {
        return new ResponseEntity<Object>(
                this.facultyDepartmentService.findById(id),
                HttpStatus.FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteFacultyDepartmentById(@PathVariable Long id) {
        this.facultyDepartmentService.deleteFacultyDepartment(id);

        return new ResponseEntity<Object>(
                HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> updateFacultyDepartment(@RequestBody FacultyDepartmentDto facultyDepartmentDto) {
        return new ResponseEntity<Object>(
                this.facultyDepartmentService.updateFacultyDepartment(facultyDepartmentDto),
                HttpStatus.OK);
    }
}
