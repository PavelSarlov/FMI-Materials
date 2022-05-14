package com.fmi.materials.controller;

import java.util.List;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.dto.section.SectionDto;
import com.fmi.materials.service.CourseService;
import com.fmi.materials.vo.CourseGroup;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<Object> createCourse(@RequestBody CourseDto courseDto) {
        return new ResponseEntity<Object>(
                this.courseService.createCourse(courseDto),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> findAllCourses() {
        return new ResponseEntity<Object>(
                this.courseService.findAllCourses(),
                HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findCourseById(@PathVariable Long id) {
        return new ResponseEntity<Object>(
                this.courseService.findById(id),
                HttpStatus.FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCourseById(@PathVariable Long id) {
        this.courseService.deleteCourse(id);

        return new ResponseEntity<Object>(
                HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> updateCourse(@RequestBody CourseDtoWithId courseDto) {
        return new ResponseEntity<Object>(
                this.courseService.updateCourse(courseDto),
                HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<Object> getCoursesByName(@RequestParam String name) {
        return new ResponseEntity<Object>(
                this.courseService.findAllCoursesByName(name),
                HttpStatus.OK);
    }

    @GetMapping("{courseId}/sections")
    public ResponseEntity<Object> getCourseSections(@PathVariable Long courseId) {
        return new ResponseEntity<Object>(
                this.courseService.findAllCourseSections(courseId),
                HttpStatus.OK);
    }

    @PostMapping("{courseId}/sections")
    public ResponseEntity<Object> createCourseSection(@PathVariable Long courseId,
            @RequestBody SectionDto sectionDto) {
        return new ResponseEntity<Object>(
                this.courseService.createSection(sectionDto, courseId),
                HttpStatus.OK);
    }

    @DeleteMapping("sections/{sectionId}")
    public ResponseEntity<Object> createCourseSection(@PathVariable Long sectionId) {
        this.courseService.deleteSection(sectionId);
        return new ResponseEntity<Object>(
                HttpStatus.OK);
    }

    @GetMapping("/template")
    public ResponseEntity<Object> getTemplate() {
        return new ResponseEntity<Object>(
                new CourseDtoWithId(
                        0L,
                        "Web Development with Java",
                        "Spring Boot",
                        "Nqkoi Sitam",
                        new FacultyDepartmentDto(6L, "Information Technologies"),
                        CourseGroup.CSF,
                        null),
                HttpStatus.OK);
    }
}
