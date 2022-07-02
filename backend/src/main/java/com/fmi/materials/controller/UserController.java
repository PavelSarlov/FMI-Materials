package com.fmi.materials.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.courselist.CourseListDto;
import com.fmi.materials.dto.courselist.CourseListDtoWithId;
import com.fmi.materials.dto.materialrequest.MaterialRequestDto;
import com.fmi.materials.dto.materialrequest.MaterialRequestDtoWithData;
import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.response.ResponseDtoSuccess;
import com.fmi.materials.service.CourseListService;
import com.fmi.materials.service.FavouriteCoursesService;
import com.fmi.materials.service.UserService;

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
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users/{userId}")
public class UserController {

    private final CourseListService courseListService;
    private final FavouriteCoursesService favouriteCoursesService;
    private final UserService userService;
    private final Validator validator;

    @GetMapping("lists")
    public ResponseEntity<List<CourseListDtoWithId>> getCourseLists(@PathVariable Long userId) {
        return new ResponseEntity<List<CourseListDtoWithId>>(
                this.courseListService.getAllCourseLists(userId),
                HttpStatus.OK);
    }

    @GetMapping("lists/{courseListId}")
    public ResponseEntity<CourseListDtoWithId> getCourseListById(@PathVariable Long userId,
            @PathVariable Long courseListId) {
        return new ResponseEntity<CourseListDtoWithId>(
                this.courseListService.getCourseList(courseListId, userId),
                HttpStatus.OK);
    }

    @GetMapping("favourite-courses")
    public ResponseEntity<List<CourseDtoWithId>> getFavouriteCourses(@PathVariable Long userId) {
        return new ResponseEntity<List<CourseDtoWithId>>(
                this.favouriteCoursesService.getFavouriteCourses(userId),
                HttpStatus.OK);
    }

    @PostMapping("lists")
    public ResponseEntity<CourseListDtoWithId> createCourseList(@PathVariable Long userId,
            @RequestBody CourseListDto courseListDto) {
        return new ResponseEntity<CourseListDtoWithId>(
                this.courseListService.createCourseList(courseListDto, userId),
                HttpStatus.CREATED);
    }

    @PostMapping("lists/{courseListId}/{courseId}")
    public ResponseEntity<CourseListDtoWithId> addCourseToList(@PathVariable Long userId,
            @PathVariable Long courseListId, @PathVariable Long courseId) {
        return new ResponseEntity<CourseListDtoWithId>(
                this.courseListService.addCourseToList(courseId, courseListId, userId),
                HttpStatus.CREATED);
    }

    @PostMapping("favourite-courses/{courseId}")
    public ResponseEntity<List<CourseDtoWithId>> addCourseToFavourite(@PathVariable Long userId,
            @PathVariable Long courseId) {
        return new ResponseEntity<List<CourseDtoWithId>>(
                this.favouriteCoursesService.addCourse(userId, courseId),
                HttpStatus.OK);
    }

    @PostMapping("material-requests/{sectionId}")
    public ResponseEntity<MaterialRequestDto> addMaterialRequest(@RequestParam("file") MultipartFile file,
            @PathVariable Long sectionId, @PathVariable Long userId) throws IOException {
        MaterialRequestDto materialRequestDto = new MaterialRequestDtoWithData(null, file.getContentType(),
                file.getOriginalFilename(), null, null, file.getBytes());

        Set<ConstraintViolation<MaterialRequestDto>> violations = validator.validate(materialRequestDto);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        return new ResponseEntity<MaterialRequestDto>(
                this.userService.createMaterialRequest(materialRequestDto, sectionId, userId),
                HttpStatus.CREATED);
    }

    @DeleteMapping("lists/{listId}")
    public ResponseEntity<ResponseDto> deleteCourseList(@PathVariable Long userId, @PathVariable Long listId) {
        this.courseListService.deleteCourseList(userId, listId);

        return new ResponseEntity<ResponseDto>(
                new ResponseDtoSuccess(HttpStatus.OK,
                        String.format("Course list with id = '%s' deleted successfully", listId)),
                HttpStatus.OK);
    }

    @DeleteMapping("lists/{listId}/{courseId}")
    public ResponseEntity<ResponseDto> deleteCourseFromCourseList(@PathVariable Long userId, @PathVariable Long listId,
            @PathVariable Long courseId) {
        this.courseListService.deleteCourseFromCourseList(userId, listId, courseId);

        return new ResponseEntity<ResponseDto>(
                new ResponseDtoSuccess(HttpStatus.OK,
                        String.format("Course with id = '%s' from list with id = '%s' deleted successfully", courseId,
                                listId)),
                HttpStatus.OK);
    }

    @DeleteMapping("favourite-courses/{courseId}")
    public ResponseEntity<ResponseDto> deleteCourseFromFavourite(@PathVariable Long userId,
            @PathVariable Long courseId) {
        this.favouriteCoursesService.deleteFavouriteCourse(userId, courseId);

        return new ResponseEntity<ResponseDto>(
                new ResponseDtoSuccess(HttpStatus.OK,
                        String.format("Course with id = '%s' from favourite courses deleted successfully", courseId)),
                HttpStatus.OK);
    }

    @PutMapping("lists")
    public ResponseEntity<CourseListDtoWithId> updateCourseList(@PathVariable Long userId,
            @RequestBody CourseListDtoWithId courseListDtoWithId) {
        return new ResponseEntity<CourseListDtoWithId>(
                this.courseListService.updateCourseList(userId, courseListDtoWithId),
                HttpStatus.OK);
    }

    @PutMapping("lists/{listId}")
    public ResponseEntity<CourseListDtoWithId> updateCourseListName(@PathVariable Long userId,
            @PathVariable Long listId, @RequestParam(name = "listName") String courseListName) {
        return new ResponseEntity<CourseListDtoWithId>(
                this.courseListService.changeCourseListName(userId, listId, courseListName),
                HttpStatus.OK);
    }
}
