package com.fmi.materials.controller;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity createCourse(@RequestBody CourseDto courseDto) {
        try {
            return new ResponseEntity(
                    this.courseService.createCourse(courseDto),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    courseDto,
                    HttpStatus.CONFLICT
            );
        }
    }

    @GetMapping
    public ResponseEntity findAllCourses() {
        return new ResponseEntity(
                this.courseService.findAllCourses(),
                HttpStatus.OK
        );
    }

    @GetMapping("{id}")
    public ResponseEntity findCourseById(@PathVariable Long id) {
        try {
            return new ResponseEntity(
                    this.courseService.findById(id),
                    HttpStatus.FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity(
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
    public ResponseEntity updateCourse(@RequestBody CourseDtoWithId courseDto) {
        try {
            return new ResponseEntity(
                    this.courseService.updateCourse(courseDto),
                    HttpStatus.FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    courseDto,
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
