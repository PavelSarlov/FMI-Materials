package com.fmi.materials.service.impl;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.pagedresult.PagedResultDto;
import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.response.ResponseDtoSuccess;
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
import com.fmi.materials.specification.CourseSpecification;
import com.fmi.materials.vo.ExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
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
            throw new EntityAlreadyExistsException(ExceptionMessage.ALREADY_EXISTS.getFormattedMessage("Course", "name", courseDto.getName()));
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
    public ResponseDto deleteCourse(Long courseId) {
        if (!this.courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId));
        }
        this.courseRepository.deleteById(courseId);

        return new ResponseDtoSuccess(HttpStatus.OK, String.format("Course with id = '%s' deleted successfully", courseId));
    }

    @Override
    public CourseDto updateCourse(CourseDtoWithId courseDto) {
        if (!this.courseRepository.existsById(courseDto.getId())) {
            throw new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseDto.getId()));
        }
        Course course = this.courseDtoMapper.convertToEntityWithId(courseDto);

        return this.courseDtoMapper.convertToDtoWithId(this.courseRepository.save(course));
    }

    @Override
    public CourseDto findById(Long courseId) {
        return this.courseDtoMapper.convertToDtoWithId(this.courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId))));
    }

    @Override
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
    public List<SectionDto> findAllCourseSections(Long courseId) {
        return this.courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId)))
                .getSections().stream()
                .map(this.sectionDtoMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SectionDto createSection(SectionDto sectionDto, Long courseId) {
        Course course = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId)));
        Section section = this.sectionDtoMapper.convertToEntity(sectionDto);
        section.setCourse(course);
        section = this.sectionRepository.save(section);
        return this.sectionDtoMapper.convertToDto(section);
    }

    @Override
    public SectionDto findSectionById(Long sectionId) {
        return this.sectionDtoMapper.convertToDto(this.sectionRepository.findById(sectionId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Section", "id", sectionId))));
    }

    @Override
    public ResponseDto deleteSection(Long sectionId) {
        if (!this.sectionRepository.existsById(sectionId)) {
            throw new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Section", "id", sectionId));
        }
        this.sectionRepository.deleteById(sectionId);

        return new ResponseDtoSuccess(HttpStatus.OK, String.format("Section with id = '%s' deleted successfully", sectionId));
    }

    @Override
    public SectionDto patchSection(SectionDto sectionDto) throws IllegalAccessException {
        Section section = this.sectionRepository.findById(sectionDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Section", "id", sectionDto.getId())));

        sectionDto.setId(null);

        for (Field field : sectionDto.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (field.get(sectionDto) != null) {
                Field fieldEntity = ReflectionUtils.findField(Section.class, field.getName());
                fieldEntity.setAccessible(true);
                ReflectionUtils.setField(fieldEntity, section, field.get(sectionDto));
            }
        }

        return this.sectionDtoMapper.convertToDto(this.sectionRepository.save(section));
    }

    @Override
    public MaterialDto createMaterial(MaterialDto materialDto, Long sectionId) throws IOException {
        Section section = this.sectionRepository.findById(sectionId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Section", "id", sectionId)));

        if (this.materialRepository.findByName(materialDto.getFileName(), section.getId()).isPresent()) {
            throw new EntityAlreadyExistsException(ExceptionMessage.ALREADY_EXISTS.getFormattedMessage("Material", "filename", materialDto.getFileName()));
        }

        Material material = this.materialDtoMapper.convertToEntity(materialDto);
        material.setSection(section);
        material.setData(((MaterialDtoWithData)materialDto).getData());

        return this.materialDtoMapper.convertToDto(this.materialRepository.save(material));
    }

    @Override
    public ResponseDto deleteMaterial(Long materialId) {
        if (!this.materialRepository.existsById(materialId)) {
            throw new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Material", "id", materialId));
        }
        this.materialRepository.deleteById(materialId);

        return new ResponseDtoSuccess(HttpStatus.OK, String.format("Material with id = '%s' deleted successfully", materialId));
    }

    @Override
    public MaterialDtoWithData findMaterialById(Long materialId) {
        return this.materialDtoMapper.convertToDtoWithData(this.materialRepository.findById(materialId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Material", "id", materialId))));
    }

    @Override
    public MaterialDtoWithData findCourseMaterialByName(Long sectionId, String name) {
        return this.materialDtoMapper.convertToDtoWithData(this.materialRepository.findByName(name, sectionId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Material", "name", name))));
    }
}
