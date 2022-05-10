package com.fmi.materials.controller;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.dto.section.SectionDto;
import com.fmi.materials.service.CourseService;
import com.fmi.materials.vo.CourseGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/courses")
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
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    courseDto,
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping("search")
    public ResponseEntity getCoursesByName(@RequestParam String name) {
        try {
            return new ResponseEntity(
                    this.courseService.findAllCoursesByName(name),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("{courseId}/sections")
    public ResponseEntity getCourseSections(@PathVariable Long courseId) {
        try {
            return new ResponseEntity(
                    this.courseService.findAllCourseSections(courseId),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("{courseId}/sections")
    public ResponseEntity createCourseSection(@PathVariable Long courseId, @RequestBody SectionDto sectionDto) {
        try {
            return new ResponseEntity(
                    this.courseService.createSection(sectionDto, courseId),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @DeleteMapping("sections/{sectionId}")
    public ResponseEntity createCourseSection(@PathVariable Long sectionId) {
        try {
            this.courseService.deleteSection(sectionId);
            return new ResponseEntity(
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/template")
    public ResponseEntity getTemplate() {
        return new ResponseEntity(
                new CourseDtoWithId(
                        0L,
                        "Web Development with Java",
                        "Spring Boot",
                        "Nqkoi Sitam",
                        new FacultyDepartmentDto(6L,"Information Technologies"),
                        CourseGroup.CSF,
                        null
                ),
                HttpStatus.OK
        );
    }
}
