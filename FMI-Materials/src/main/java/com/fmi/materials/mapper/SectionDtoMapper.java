package com.fmi.materials.mapper;

import com.fmi.materials.dto.section.SectionDto;
import com.fmi.materials.model.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SectionDtoMapper {

    @Autowired
    private MaterialDtoMapper materialDtoMapper;

    public Section convertToEntity(SectionDto sectionDto) {
        return new Section(
                sectionDto.getId(),
                sectionDto.getName(),
                null,
                null
        );
    }

    public SectionDto convertToDto(Section section) {
        return new SectionDto(
                section.getId(),
                section.getName(),
                null,
                section.getMaterials().stream()
                        .map(this.materialDtoMapper::convertToDto)
                        .collect(Collectors.toList())
        );
    }
}
