package com.fmi.materials.controller;

import com.fmi.materials.dto.CourseDto;
import com.fmi.materials.mapper.CourseDtoMapper;
import com.fmi.materials.model.Course;
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
    @Autowired
    private CourseDtoMapper courseDtoMapper;

    @PostMapping("courses")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        try {
            Course course = courseDtoMapper.convertToEntity(courseDto);
            course = this.courseService.createCourse(course);

            return new ResponseEntity<CourseDto>(
                    courseDtoMapper.convertToDto(course),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<CourseDto>(
                    courseDto,
                    HttpStatus.CONFLICT
            );
        }
    }
}
