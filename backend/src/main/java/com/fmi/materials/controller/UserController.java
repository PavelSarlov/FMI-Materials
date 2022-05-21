package com.fmi.materials.controller;

import com.fmi.materials.dto.CourseCourseListIdDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.courselist.CourseListDto;
import com.fmi.materials.dto.courselist.CourseListDtoWithId;
import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.response.ResponseDtoSuccess;
import com.fmi.materials.service.CourseListService;
import com.fmi.materials.service.FavouriteCoursesService;
import com.fmi.materials.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users/{userId}")
public class UserController {
    private CourseListService courseListService;
    private FavouriteCoursesService favouriteCoursesService;

    @Autowired
    public UserController(CourseListService courseListService, FavouriteCoursesService favouriteCoursesService) {
        this.courseListService = courseListService;
        this.favouriteCoursesService = favouriteCoursesService;
    }

    @GetMapping("lists")
    public ResponseEntity<List<CourseListDtoWithId>> getCourseLists(@PathVariable Long userId) {
        return new ResponseEntity<List<CourseListDtoWithId>>(
                this.courseListService.getAllCourseLists(userId),
                HttpStatus.OK
        );
    }

    @GetMapping("lists/{courseListId}")
    public ResponseEntity<CourseListDtoWithId> getCourseListById(@PathVariable Long userId, @PathVariable Long courseListId) {
        return new ResponseEntity<CourseListDtoWithId>(
                this.courseListService.getCourseList(courseListId, userId),
                HttpStatus.OK
        );
    }

    @GetMapping("favourite-courses")
    public ResponseEntity<List<CourseDtoWithId>> getFavouriteCourses(@PathVariable Long userId) {
        return new ResponseEntity<List<CourseDtoWithId>>(
                this.favouriteCoursesService.getFavouriteCourses(userId),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<CourseListDtoWithId> createCourseList(@PathVariable Long userId, @RequestBody CourseListDto courseListDto) {
        return new ResponseEntity<CourseListDtoWithId>(
                this.courseListService.createCourseList(courseListDto, userId),
                HttpStatus.CREATED
        );
    }

    @PostMapping("lists")
    public ResponseEntity<CourseListDtoWithId> addCourseToList(@PathVariable Long userId, @RequestBody CourseCourseListIdDto courseCourseListIdDto) {
        return new ResponseEntity<CourseListDtoWithId>(
                this.courseListService.addCourseToList(courseCourseListIdDto.getCourseId(), courseCourseListIdDto.getCourseListId(), userId),
                HttpStatus.CREATED
        );
    }

    @PostMapping("favourite-courses/{courseId}")
    public ResponseEntity<List<CourseDtoWithId>> addCourseToFavourite(@PathVariable Long userId, @PathVariable Long courseId) {
        return new ResponseEntity<List<CourseDtoWithId>>(
                this.favouriteCoursesService.addCourse(userId, courseId),
                HttpStatus.OK
        );
    }

    @DeleteMapping("lists/{listId}")
    public ResponseEntity<ResponseDto> deleteCourseList(@PathVariable Long userId, @PathVariable Long listId) {
        this.courseListService.deleteCourseList(userId, listId);

        return new ResponseEntity<ResponseDto>(
                new ResponseDtoSuccess(HttpStatus.OK, String.format("Course list with id = '%s' deleted successfully", listId)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("lists/{listId}/courses/{courseId}")
    public ResponseEntity<ResponseDto> deleteCourseFromCourseList(@PathVariable Long userId, @PathVariable Long listId, @PathVariable Long courseId) {
        this.courseListService.deleteCourseFromCourseList(userId, listId, courseId);

        return new ResponseEntity<ResponseDto>(
                new ResponseDtoSuccess(HttpStatus.OK, String.format("Course with id = '%s' from list with id = '%s' deleted successfully", courseId, listId)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("favourite-courses/{courseId}")
    public ResponseEntity<ResponseDto> deleteCourseFromFavourite(@PathVariable Long userId, @PathVariable Long courseId) {
        this.favouriteCoursesService.deleteFavouriteCourse(userId, courseId);

        return new ResponseEntity<ResponseDto>(
                new ResponseDtoSuccess(HttpStatus.OK, String.format("Course with id = '%s' from favourite courses deleted successfully", courseId)),
                HttpStatus.OK
        );
    }

    @PutMapping("lists")
    public ResponseEntity<CourseListDtoWithId> updateCourseList(@PathVariable Long userId, @RequestBody CourseListDtoWithId courseListDtoWithId) {
        return new ResponseEntity<CourseListDtoWithId>(
                this.courseListService.updateCourseList(userId, courseListDtoWithId),
                HttpStatus.OK
        );
    }
}