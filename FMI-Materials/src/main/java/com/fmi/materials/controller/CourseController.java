package com.fmi.materials.controller;

import com.fmi.materials.dto.CourseDto;
import com.fmi.materials.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("courses")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        try {
            return new ResponseEntity<CourseDto>(
                    this.courseService.createCourse(courseDto),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<CourseDto>(
                    courseDto,
                    HttpStatus.CONFLICT
            );
        }
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> findAllCourses() {
        return new ResponseEntity<List<CourseDto>>(
                this.courseService.findAllCourses(),
                HttpStatus.OK
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<CourseDto> findCourseById(@PathVariable Long id) {
        try {
            return new ResponseEntity<CourseDto>(
                    this.courseService.findById(id),
                    HttpStatus.FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity<CourseDto>(
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteCourseById(@PathVariable Long id) {
        try {
            this.courseService.deleteCourse(id);

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
    public ResponseEntity<CourseDto> updateCourse(@RequestBody CourseDto courseDto) {
        try {
            return new ResponseEntity<CourseDto>(
                    this.courseService.updateCourse(courseDto),
                    HttpStatus.FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity<CourseDto>(
                    courseDto,
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
