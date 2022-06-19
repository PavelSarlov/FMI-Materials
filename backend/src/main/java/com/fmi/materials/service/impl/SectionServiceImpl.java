package com.fmi.materials.service.impl;

import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.response.ResponseDtoSuccess;
import com.fmi.materials.dto.section.SectionDto;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.mapper.CourseDtoMapper;
import com.fmi.materials.mapper.SectionDtoMapper;
import com.fmi.materials.model.Section;
import com.fmi.materials.repository.SectionRepository;
import com.fmi.materials.service.CourseService;
import com.fmi.materials.service.SectionService;
import com.fmi.materials.vo.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@Service
public class SectionServiceImpl implements SectionService {
    @Autowired
    private CourseService courseService;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private SectionDtoMapper sectionDtoMapper;
    @Autowired
    private CourseDtoMapper courseDtoMapper;

    @Override
    public SectionDto createSection(SectionDto sectionDto, Long courseId) {
        Section section = this.sectionDtoMapper.convertToEntity(sectionDto);
        section.setCourse(courseDtoMapper.convertToEntityWithId((CourseDtoWithId) this.courseService.findById(courseId)));
        section = this.sectionRepository.save(section);
        return this.sectionDtoMapper.convertToDto(section);
    }

    @Override
    public SectionDto findSectionById(Long sectionId) {
        return this.sectionDtoMapper.convertToDto(this.sectionRepository.findById(sectionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("Section", "id", sectionId))));
    }

    @Override
    public ResponseDto deleteSection(Long sectionId) {
        if (!this.sectionRepository.existsById(sectionId)) {
            throw new EntityNotFoundException(
                    ExceptionMessage.NOT_FOUND.getFormattedMessage("Section", "id", sectionId));
        }
        this.sectionRepository.deleteById(sectionId);

        return new ResponseDtoSuccess(HttpStatus.OK,
                String.format("Section with id = '%s' deleted successfully", sectionId));
    }

    @Override
    public SectionDto patchSection(SectionDto sectionDto) throws IllegalAccessException {
        Section section = this.sectionRepository.findById(sectionDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("Section", "id", sectionDto.getId())));

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
}
