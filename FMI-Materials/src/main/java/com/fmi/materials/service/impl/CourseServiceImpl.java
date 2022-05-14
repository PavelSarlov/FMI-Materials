package com.fmi.materials.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.section.SectionDto;
import com.fmi.materials.exception.EntityAlreadyExistsException;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.mapper.CourseDtoMapper;
import com.fmi.materials.mapper.FacultyDepartmentDtoMapper;
import com.fmi.materials.mapper.MaterialDtoMapper;
import com.fmi.materials.mapper.SectionDtoMapper;
import com.fmi.materials.model.Course;
import com.fmi.materials.model.FacultyDepartment;
import com.fmi.materials.model.Material;
import com.fmi.materials.model.Section;
import com.fmi.materials.repository.CourseRepository;
import com.fmi.materials.repository.MaterialRepository;
import com.fmi.materials.repository.SectionRepository;
import com.fmi.materials.service.CourseService;
import com.fmi.materials.service.FacultyDepartmentService;
import com.fmi.materials.vo.ErrorMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private FacultyDepartmentService facultyDepartmentService;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private CourseDtoMapper courseDtoMapper;
    @Autowired
    private FacultyDepartmentDtoMapper facultyDepartmentDtoMapper;
    @Autowired
    private SectionDtoMapper sectionDtoMapper;
    @Autowired
    private MaterialDtoMapper materialDtoMapper;

    @Override
    public CourseDto createCourse(CourseDto courseDto) {

        List<String> courseNames = this.courseRepository.findAll().stream()
                .map(c -> c.getName().toLowerCase(Locale.ROOT))
                .collect(Collectors.toList());
        if (courseNames.contains(courseDto.getName().toLowerCase(Locale.ROOT))) {
            throw new EntityAlreadyExistsException(
                    ErrorMessage.ALREADY_EXISTS.getFormattedMessage("Course", "name", courseDto.getName()));
        }

        Course course = this.courseDtoMapper.convertToEntity(courseDto);
        FacultyDepartment facultyDepartment = this.facultyDepartmentDtoMapper
                .convertToEntity(this.facultyDepartmentService.findById(courseDto.getFacultyDepartmentDto().getId()));
        course.setFacultyDepartment(facultyDepartment);
        course = this.courseRepository.save(course);

        Section defaultSection = new Section("Home", course, null);
        this.sectionRepository.save(defaultSection);

        course.setSections(Stream.of(defaultSection).collect(Collectors.toSet()));

        return this.courseDtoMapper.convertToDtoWithId(course);
    }

    @Override
    public void deleteCourse(Long courseId) {
        if (!this.courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException(ErrorMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId));
        }
        this.courseRepository.deleteById(courseId);
    }

    @Override
    public CourseDto updateCourse(CourseDtoWithId courseDto) {
        if (!this.courseRepository.existsById(courseDto.getId())) {
            throw new EntityNotFoundException(ErrorMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseDto.getId()));
        }
        Course course = this.courseDtoMapper.convertToEntityWithId(courseDto);
        return this.courseDtoMapper.convertToDtoWithId(this.courseRepository.save(course));
    }

    @Override
    public CourseDto findById(Long courseId) {
        return this.courseDtoMapper.convertToDtoWithId(this.courseRepository.findById(courseId)
                .orElseThrow(
                        () -> new EntityNotFoundException(ErrorMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId))));
    }

    @Override
    public List<CourseDto> findAllCourses() {
        return this.courseRepository.findAll().stream()
                .map(this.courseDtoMapper::convertToDtoWithId)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> findAllCoursesByName(String name) {
        List<String> keyWords = Arrays.stream(name.split("[\\p{Punct}\\p{Blank}]"))
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        List<CourseDto> courses = this.courseRepository.findAll().stream()
                .filter(c -> {
                    for (String w : keyWords) {
                        if (!c.getName().toLowerCase(Locale.ROOT).contains(w)) {
                            return false;
                        }
                    }
                    return true;
                })
                .map(this.courseDtoMapper::convertToDtoWithId)
                .collect(Collectors.toList());

        return courses;
    }

    @Override
    public List<SectionDto> findAllCourseSections(Long courseId) {
        return this.courseRepository.findById(courseId)
                .orElseThrow(
                        () -> new EntityNotFoundException(ErrorMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId)))
                .getSections().stream()
                .map(this.sectionDtoMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SectionDto createSection(SectionDto sectionDto, Long courseId) {
        Course course = this.courseRepository.findById(courseId)
                .orElseThrow(
                        () -> new EntityNotFoundException(ErrorMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId)));
        Section section = this.sectionDtoMapper.convertToEntity(sectionDto);
        section.setCourse(course);
        section = this.sectionRepository.save(section);
        return this.sectionDtoMapper.convertToDto(section);
    }

    @Override
    public void deleteSection(Long sectionId) {
        if (!this.sectionRepository.existsById(sectionId)) {
            throw new EntityNotFoundException(ErrorMessage.NOT_FOUND.getFormattedMessage("Section", "id", sectionId));
        }
        this.sectionRepository.deleteById(sectionId);
    }

    @Override
    public MaterialDto createMaterial(MultipartFile materialFile, Long sectionId) throws IOException {
        Section section = this.sectionRepository.findById(sectionId)
                .orElseThrow(
                        () -> new EntityNotFoundException(ErrorMessage.NOT_FOUND.getFormattedMessage("Section", "id", sectionId)));
        Material material = this.materialDtoMapper.convertToEntity(materialFile, section);
        material = this.materialRepository.save(material);
        return this.materialDtoMapper.convertToDto(material);
    }

    @Override
    public void deleteMaterial(Long materialId) {
        if (!this.materialRepository.existsById(materialId)) {
            throw new EntityNotFoundException(ErrorMessage.NOT_FOUND.getFormattedMessage("Material", "id", materialId));
        }
        this.materialRepository.deleteById(materialId);
    }
}
