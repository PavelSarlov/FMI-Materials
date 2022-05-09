package com.fmi.materials.controller;

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

    /*@GetMapping("{id}")
    public ResponseEntity<UserDto> findCourseById(@PathVariable Long id) {
        try {
            return new ResponseEntity(
                    this.userService.findUserById(id),
                    HttpStatus.FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    HttpStatus.NOT_FOUND
            );
        }
    }*/

    /*@PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDtoRegistration userDto)
    {
        try {
            return new ResponseEntity(
                    this.userService.createUser(userDto),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    userDto,
                    HttpStatus.CONFLICT
            );
        }
    }*/

    /*@DeleteMapping("{id}")
    public ResponseEntity deleteUserById(@PathVariable Long id) {
        try {
            this.userService.deleteUserById(id);

            return new ResponseEntity(
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    HttpStatus.NOT_FOUND
            );
        }
    }*/

    /*@PutMapping
    public ResponseEntity<UserDtoWithId> updateCourse(@RequestBody UserDtoWithId userDtoWithId) {
        try {
            return new ResponseEntity(
                    this.userService.updateUser(userDtoWithId),
                    HttpStatus.FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    userDtoWithId,
                    HttpStatus.NOT_FOUND
            );
        }
    }*/

    @GetMapping
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
}
