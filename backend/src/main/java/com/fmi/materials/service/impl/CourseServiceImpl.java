package com.fmi.materials.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.pagedresult.PagedResultDto;
import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.response.ResponseDtoSuccess;
import com.fmi.materials.dto.section.SectionDto;
import com.fmi.materials.exception.EntityAlreadyExistsException;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.mapper.CourseDtoMapper;
import com.fmi.materials.mapper.FacultyDepartmentDtoMapper;
import com.fmi.materials.mapper.SectionDtoMapper;
import com.fmi.materials.model.Course;
import com.fmi.materials.model.FacultyDepartment;
import com.fmi.materials.model.Section;
import com.fmi.materials.repository.CourseRepository;
import com.fmi.materials.repository.SectionRepository;
import com.fmi.materials.service.CourseService;
import com.fmi.materials.service.FacultyDepartmentService;
import com.fmi.materials.specification.CourseSpecification;
import com.fmi.materials.vo.ExceptionMessage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final FacultyDepartmentService facultyDepartmentService;
    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;
    private final CourseDtoMapper courseDtoMapper;
    private final FacultyDepartmentDtoMapper facultyDepartmentDtoMapper;
    private final SectionDtoMapper sectionDtoMapper;

    @Override
    @Transactional
    public CourseDto createCourse(CourseDto courseDto) {

        List<String> courseNames = this.courseRepository.findAll().stream()
                .map(c -> c.getName().toLowerCase(Locale.ROOT))
                .collect(Collectors.toList());
        if (courseNames.contains(courseDto.getName().toLowerCase(Locale.ROOT))) {
            throw new EntityAlreadyExistsException(
                    ExceptionMessage.ALREADY_EXISTS.getFormattedMessage("Course", "name", courseDto.getName()));
        }

        Course course = this.courseDtoMapper.convertToEntity(courseDto);
        FacultyDepartment facultyDepartment = this.facultyDepartmentDtoMapper
                .convertToEntity(this.facultyDepartmentService.findById(courseDto.getFacultyDepartmentDto().getId()));
        course.setFacultyDepartment(facultyDepartment);

        course = this.courseRepository.save(course);

        Section defaultSection = new Section("Home", course, null, null);
        this.sectionRepository.save(defaultSection);

        course.setSections(Stream.of(defaultSection).collect(Collectors.toSet()));

        return this.courseDtoMapper.convertToDtoWithId(course);
    }

    @Override
    @Transactional
    public ResponseDto deleteCourse(Long courseId) {
        if (!this.courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId));
        }
        this.courseRepository.deleteById(courseId);

        return new ResponseDtoSuccess(HttpStatus.OK,
                String.format("Course with id = '%s' deleted successfully", courseId));
    }

    @Override
    @Transactional
    public CourseDto updateCourse(CourseDtoWithId courseDto) {
        if (!this.courseRepository.existsById(courseDto.getId())) {
            throw new EntityNotFoundException(
                    ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseDto.getId()));
        }
        Course course = this.courseDtoMapper.convertToEntityWithId(courseDto);

        return this.courseDtoMapper.convertToDtoWithId(this.courseRepository.save(course));
    }

    @Override
    @Transactional
    public CourseDto findById(Long courseId) {
        return this.courseDtoMapper.convertToDtoWithId(this.courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId))));
    }

    @Override
    @Transactional
    public PagedResultDto<CourseDto> findCourses(String filter, String filterValue, Pageable pageable) {
        List<String> keyWords = Arrays.stream(filterValue.split("[\\p{Punct}\\p{Blank}]"))
                .collect(Collectors.toList());
        CourseSpecification spec = new CourseSpecification(filter, keyWords);

        Page<Course> page = this.courseRepository.findAll(spec, pageable);
        return PagedResultDto.<CourseDto>builder()
                .items(page.getContent().stream()
                        .map(c -> {
                            CourseDto cd = courseDtoMapper.convertToDtoWithId(c);
                            cd.setSectionDtos(null);
                            return cd;
                        })
                        .collect(Collectors.toList()))
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalItems(page.getTotalElements())
                .itemsPerPage(pageable.getPageSize())
                .first(!page.isFirst())
                .last(!page.isLast())
                .build();
    }

    @Override
    @Transactional
    public List<SectionDto> findAllCourseSections(Long courseId) {
        return this.courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId)))
                .getSections().stream()
                .map(this.sectionDtoMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
