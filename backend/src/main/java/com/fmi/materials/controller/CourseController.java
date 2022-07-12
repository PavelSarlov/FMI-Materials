package com.fmi.materials.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.response.ResponseDtoSuccess;
import com.fmi.materials.dto.section.SectionDto;
import com.fmi.materials.service.CourseService;
import com.fmi.materials.service.MaterialService;
import com.fmi.materials.service.SectionService;
import com.fmi.materials.vo.CourseGroup;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping(value = "api/courses")
public class CourseController {

    private final CourseService courseService;
    private final SectionService sectionService;
    private final MaterialService materialService;
    private final Validator validator;

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody @Valid CourseDto courseDto) {
        return new ResponseEntity<CourseDto>(
                this.courseService.createCourse(courseDto),
                HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<CourseDto> findCourseById(@PathVariable Long id) {
        return new ResponseEntity<CourseDto>(
                this.courseService.findById(id),
                HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseDto> deleteCourseById(@PathVariable Long id) {
        this.courseService.deleteCourse(id);

        return new ResponseEntity<ResponseDto>(
                new ResponseDtoSuccess(HttpStatus.OK, String.format("Course with id = '%s' deleted successfully", id)),
                HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CourseDto> updateCourse(@RequestBody @Valid CourseDtoWithId courseDto) {
        return new ResponseEntity<CourseDto>(
                this.courseService.updateCourse(courseDto),
                HttpStatus.OK);
    }

    @PatchMapping("sections")
    public ResponseEntity<SectionDto> patchSection(@RequestBody @Valid SectionDto sectionDto) throws IllegalAccessException {
        return new ResponseEntity<SectionDto>(
                this.sectionService.patchSection(sectionDto),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> findCourses(@RequestParam(defaultValue = "name") String filter, @RequestParam(defaultValue = "") String filterValue, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "false") Boolean desc) {

        Sort sort = Sort.by((sortBy != null && sortBy != "" ? sortBy : "name"));
        if (desc != null && desc) sort = sort.descending();
        Pageable pageable = PageRequest.of(page, size, sort);

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

    @GetMapping("/sections/{sectionId}")
    public ResponseEntity<SectionDto> getCourseSectionById(@PathVariable Long sectionId) {
        return new ResponseEntity<SectionDto>(
                this.sectionService.findSectionById(sectionId),
                HttpStatus.OK);
    }

    @PostMapping("{courseId}/sections")
    public ResponseEntity<SectionDto> createCourseSection(@PathVariable Long courseId,
                                                          @RequestBody SectionDto sectionDto) {
        return new ResponseEntity<SectionDto>(
                this.sectionService.createSection(sectionDto, courseId),
                HttpStatus.OK);
    }

    @DeleteMapping("sections/{sectionId}")
    public ResponseEntity<ResponseDto> deleteCourseSection(@PathVariable Long sectionId) {
        return new ResponseEntity<ResponseDto>(
                this.sectionService.deleteSection(sectionId),
                HttpStatus.OK);
    }

    @GetMapping("sections/materials/{materialId}")
    public ResponseEntity<byte[]> getMaterialById(@PathVariable Long materialId) {
        MaterialDtoWithData material = this.materialService.findMaterialById(materialId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(material.getFileFormat()));
        headers.add("Content-Disposition", "inline; filename=" + material.getFileName());

        return new ResponseEntity<byte[]>(
                material.getData(),
                headers,
                HttpStatus.OK
        );
    }

    @GetMapping("sections/{sectionId}/materials/{materialName}")
    public ResponseEntity<byte[]> getMaterialByName(@PathVariable Long sectionId, @PathVariable String materialName) {
        MaterialDtoWithData material = this.materialService.findCourseMaterialByName(sectionId, materialName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(material.getFileFormat()));
        headers.add("Content-Disposition", "inline; filename=" + material.getFileName());

        return new ResponseEntity<byte[]>(
                material.getData(),
                headers,
                HttpStatus.OK
        );
    }

    @PostMapping("sections/{sectionId}/materials")
    public ResponseEntity<MaterialDto> createMaterial(@RequestBody MultipartFile file, @PathVariable Long sectionId) throws IOException {
        MaterialDto materialDto = new MaterialDtoWithData(null, file.getContentType(), file.getOriginalFilename(), file.getBytes());

        Set<ConstraintViolation<MaterialDto>> violations = validator.validate(materialDto);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        return new ResponseEntity<MaterialDto>(
                this.materialService.createMaterial(materialDto, sectionId),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("sections/materials/{materialId}")
    public ResponseEntity<ResponseDto> deleteMaterial(@PathVariable Long materialId) throws IOException {
        return new ResponseEntity<ResponseDto>(
                this.materialService.deleteMaterial(materialId),
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
