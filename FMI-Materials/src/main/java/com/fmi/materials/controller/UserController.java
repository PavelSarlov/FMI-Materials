package com.fmi.materials.controller;

import com.fmi.materials.dto.CourseCourseListIdDto;
import com.fmi.materials.dto.courselist.CourseListDto;
import com.fmi.materials.dto.courselist.CourseListDtoWithId;
import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.response.ResponseDtoSuccess;
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
    public ResponseEntity<List<CourseListDtoWithId>> getCourseLists(@PathVariable Long userId) {
        return new ResponseEntity<List<CourseListDtoWithId>>(
                this.courseListService.getAllCourseLists(userId),
                HttpStatus.OK
        );
    }

    @GetMapping("/lists/{courseListId}")
    public ResponseEntity<CourseListDto> getCourseListById(@PathVariable Long userId, @PathVariable Long courseListId) {
        return new ResponseEntity<CourseListDto>(
                this.courseListService.getCourseList(courseListId, userId),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<CourseListDto> createCourseList(@PathVariable Long userId, @RequestBody CourseListDto courseListDto) {
        return new ResponseEntity<CourseListDto>(
                this.courseListService.createCourseList(courseListDto, userId),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/lists")
    public ResponseEntity<CourseListDto> addCourseToList(@PathVariable Long userId, @RequestBody CourseCourseListIdDto courseCourseListIdDto) {
        return new ResponseEntity<CourseListDto>(
                this.courseListService.addCourseToList(courseCourseListIdDto.getCourseId(), courseCourseListIdDto.getCourseListId(), userId),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/lists/{listId}")
    public ResponseEntity<ResponseDto> deleteCourseList(@PathVariable Long userId, @PathVariable Long listId) {
        this.courseListService.deleteCourseList(userId, listId);

        return new ResponseEntity<ResponseDto>(
                new ResponseDtoSuccess(HttpStatus.OK, String.format("Course list with id = '%s' deleted successfully", listId)),
                HttpStatus.OK
        );
    }

    @PutMapping("/lists")
    public ResponseEntity<CourseListDto> updateCourseList(@RequestBody CourseListDtoWithId courseListDtoWithId) {
        return new ResponseEntity<CourseListDto>(
                this.courseListService.updateCourseList(courseListDtoWithId),
                HttpStatus.OK
        );
    }
}
