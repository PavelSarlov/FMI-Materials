package com.fmi.materials.controller;

import java.io.IOException;
import java.util.List;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.response.ResponseDtoSuccess;
import com.fmi.materials.dto.section.SectionDto;
import com.fmi.materials.model.Material;
import com.fmi.materials.service.CourseService;
import com.fmi.materials.vo.CourseGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(value = "api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        return new ResponseEntity<CourseDto>(
                this.courseService.createCourse(courseDto),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> findAllCourses() {
        return new ResponseEntity<List<CourseDto>>(
                this.courseService.findAllCourses(),
                HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CourseDto> findCourseById(@PathVariable Long id) {
        return new ResponseEntity<CourseDto>(
                this.courseService.findById(id),
                HttpStatus.FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseDto> deleteCourseById(@PathVariable Long id) {
        this.courseService.deleteCourse(id);

        return new ResponseEntity<ResponseDto>(
                new ResponseDtoSuccess(HttpStatus.OK, String.format("Course with id = '%s' deleted successfully", id)),
                HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CourseDto> updateCourse(@RequestBody CourseDtoWithId courseDto) {
        return new ResponseEntity<CourseDto>(
                this.courseService.updateCourse(courseDto),
                HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<List<CourseDto>> getCoursesByName(@RequestParam String name) {
        return new ResponseEntity<List<CourseDto>>(
                this.courseService.findAllCoursesByName(name),
                HttpStatus.OK);
    }

    @GetMapping("{courseId}/sections")
    public ResponseEntity<List<SectionDto>> getCourseSections(@PathVariable Long courseId) {
        return new ResponseEntity<List<SectionDto>>(
                this.courseService.findAllCourseSections(courseId),
                HttpStatus.OK);
    }

    @PostMapping("{courseId}/sections")
    public ResponseEntity<SectionDto> createCourseSection(@PathVariable Long courseId,
            @RequestBody SectionDto sectionDto) {
        return new ResponseEntity<SectionDto>(
                this.courseService.createSection(sectionDto, courseId),
                HttpStatus.OK);
    }

    @DeleteMapping("sections/{sectionId}")
    public ResponseEntity<ResponseDto> deleteCourseSection(@PathVariable Long sectionId) {
        this.courseService.deleteSection(sectionId);
        return new ResponseEntity<ResponseDto>(
                new ResponseDtoSuccess(HttpStatus.OK, String.format("Section with id = '%s' deleted successfully", sectionId)),
                HttpStatus.OK);
    }

    //
    // Just testing methods below
    //

    @PostMapping("sections/{sectionId}/files")
    public ResponseEntity<MaterialDto> createSectionMaterial(@PathVariable Long sectionId, @RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<MaterialDto>(
                this.courseService.createMaterial(file, sectionId),
                HttpStatus.CREATED
        );
    }

    @GetMapping("{courseId}/files/{fileName}")
    public ResponseEntity<byte[]> getMaterialByName(@PathVariable Long courseId, @PathVariable String fileName) {
        MaterialDtoWithData material = this.courseService.findCourseMaterialByName(courseId, fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(material.getFileFormat()));
        headers.add("Content-Disposition", "inline; filename=" + material.getFileName());
        
        return new ResponseEntity<byte[]>(
                material.getData(),
                headers,
                HttpStatus.FOUND
                );
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
