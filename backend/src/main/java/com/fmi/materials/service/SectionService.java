package com.fmi.materials.service;

import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.section.SectionDto;

public interface SectionService {

    SectionDto createSection(SectionDto sectionDto, Long courseId);

    SectionDto findSectionById(Long sectionId);

    ResponseDto deleteSection(Long sectionId);

    SectionDto patchSection(SectionDto sectionDto) throws IllegalAccessException;
}
