package com.fmi.materials.controller;

import com.fmi.materials.dto.CourseCourseListIdDto;
import com.fmi.materials.dto.courselist.CourseListDto;
import com.fmi.materials.dto.courselist.CourseListDtoWithId;
import com.fmi.materials.service.CourseListService;
import com.fmi.materials.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users/{userId}")
public class UserController {
    private UserService userService;
    private CourseListService courseListService;

    @Autowired
    public UserController(UserService userService, CourseListService courseListService) {
        this.userService = userService;
        this.courseListService = courseListService;
    }

    @GetMapping("/lists")
    public ResponseEntity<Object> courseLists(@PathVariable Long userId) {
        return new ResponseEntity<Object>(
                this.courseListService.getAllCourseLists(userId),
                HttpStatus.OK
        );
    }

    @GetMapping("/lists/{courseListId}")
    public ResponseEntity<Object> courseList(@PathVariable Long userId, @PathVariable Long courseListId) {
        return new ResponseEntity<Object>(
                this.courseListService.getCourseList(courseListId, userId),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Object> createCourseList(@PathVariable Long userId, @RequestBody CourseListDto courseListDto) {
        return new ResponseEntity<Object>(
                this.courseListService.createCourseList(courseListDto, userId),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/lists")
    public ResponseEntity<Object> addCourseToList(@PathVariable Long userId, @RequestBody CourseCourseListIdDto courseCourseListIdDto) {
        return new ResponseEntity<Object>(
                this.courseListService.addCourseToList(courseCourseListIdDto.getCourseId(), courseCourseListIdDto.getCourseListId(), userId),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/lists/{listId}")
    public ResponseEntity<Object> deleteCourseList(@PathVariable Long userId, @PathVariable Long listId) {
        this.courseListService.deleteCourseList(userId, listId);

        return new ResponseEntity<Object>(
            HttpStatus.OK
        );
    }

    @PutMapping("/lists")
    public ResponseEntity<Object> updateCourseList(@RequestBody CourseListDtoWithId courseListDtoWithId) {
        return new ResponseEntity<Object>(
                this.courseListService.updateCourseList(courseListDtoWithId),
                HttpStatus.OK
        );
    }
}
