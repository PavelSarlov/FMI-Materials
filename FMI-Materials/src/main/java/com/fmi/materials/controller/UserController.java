package com.fmi.materials.controller;

import com.fmi.materials.dto.CourseCourseListIdDto;
import com.fmi.materials.dto.courselist.CourseListDto;
import com.fmi.materials.dto.courselist.CourseListDtoWithId;
import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.dto.user.UserDtoRegistration;
import com.fmi.materials.dto.user.UserDtoWithId;
import com.fmi.materials.model.CourseList;
import com.fmi.materials.service.CourseListService;
import com.fmi.materials.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<CourseListDtoWithId>> courseLists(@PathVariable Long userId) {
        try {
            return new ResponseEntity(
                    this.courseListService.getAllCourseLists(userId),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/lists/{courseListId}")
    public ResponseEntity<CourseListDtoWithId> courseList(@PathVariable Long userId, @PathVariable Long courseListId) {
        try {
            return new ResponseEntity(
                    this.courseListService.getCourseList(courseListId, userId),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping
    public ResponseEntity<CourseListDtoWithId> createCourseList(@PathVariable Long userId, @RequestBody CourseListDto courseListDto) {
        try {
            return new ResponseEntity(
                    this.courseListService.createCourseList(courseListDto, userId),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    // improve mapping
    @PostMapping("/add")
    public ResponseEntity<CourseListDtoWithId> addCourseToList(@PathVariable Long userId, @RequestBody CourseCourseListIdDto courseCourseListIdDto) {
        try {
            return new ResponseEntity(
                    this.courseListService.addCourseToList(courseCourseListIdDto.getCourseId(), courseCourseListIdDto.getCourseListId(), userId),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @DeleteMapping("/lists/{listId}")
    public ResponseEntity deleteCourseList(@PathVariable Long userId, @PathVariable Long listId) {
        try {
            this.courseListService.deleteCourseList(userId, listId);

            return new ResponseEntity(
                HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PutMapping("/lists")
    public ResponseEntity<CourseListDtoWithId> updateCourseList(@RequestBody CourseListDtoWithId courseListDtoWithId) {
        try {
            return new ResponseEntity(
                    this.courseListService.updateCourseList(courseListDtoWithId),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    courseListDtoWithId,
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
