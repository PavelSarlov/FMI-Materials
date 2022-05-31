package com.fmi.materials.controller;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.response.ResponseDtoSuccess;
import com.fmi.materials.dto.section.SectionDto;
import com.fmi.materials.service.CourseService;
import com.fmi.materials.vo.CourseGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<Object> findCourses(@RequestParam(defaultValue = "name") String filter, @RequestParam(defaultValue = "") String filterValue, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String sortBy, @RequestParam(required = false) Boolean desc) {

        Sort sort = Sort.by((sortBy != null ? sortBy : "name"));
        if (desc != null && desc) sort = sort.descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        return new ResponseEntity<Object>(
                this.courseService.findCourses(filter, filterValue, pageable),
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
        return new ResponseEntity<ResponseDto>(
                this.courseService.deleteSection(sectionId),
                HttpStatus.OK);
    }

    @GetMapping("sections/{sectionId}/materials/{materialName}")
    public ResponseEntity<byte[]> getMaterialByName(@PathVariable Long sectionId, @PathVariable String materialName) {
        MaterialDtoWithData material = this.courseService.findCourseMaterialByName(sectionId, materialName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(material.getFileFormat()));
        headers.add("Content-Disposition", "inline; filename=" + material.getFileName());

        return new ResponseEntity<byte[]>(
                material.getData(),
                headers,
                HttpStatus.FOUND
        );
    }

    @PostMapping("sections/{sectionId}/materials")
    public ResponseEntity<MaterialDto> createMaterial(@RequestBody MultipartFile file, @PathVariable Long sectionId) throws IOException {
        return new ResponseEntity<MaterialDto>(
                this.courseService.createMaterial(file.getContentType(), file.getOriginalFilename(), file.getBytes(), sectionId),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("sections/materials/{materialId}")
    public ResponseEntity<ResponseDto> deleteMaterial(@PathVariable Long materialId) throws IOException {
        return new ResponseEntity<ResponseDto>(
                this.courseService.deleteMaterial(materialId),
                HttpStatus.OK
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
