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
    @Autowired
    private MaterialRequestDtoMapper materialRequestDtoMapper;

    public Section convertToEntity(SectionDto sectionDto) {
        if (sectionDto == null) {
            return null;
        }

        return new Section(
                sectionDto.getId(),
                sectionDto.getName(),
                null,
                sectionDto.getMaterialDtos() != null ? sectionDto.getMaterialDtos().stream()
                        .map(this.materialDtoMapper::convertToEntity)
                        .collect(Collectors.toList()) : null,
                sectionDto.getMaterialRequestDtos() != null ? sectionDto.getMaterialRequestDtos().stream()
                        .map(this.materialRequestDtoMapper::convertToEntity)
                        .collect(Collectors.toList()) : null
        );
    }

    public SectionDto convertToDto(Section section) {
        if (section == null) {
            return null;
        }

        return new SectionDto(
                section.getId(),
                section.getName(),
                section.getMaterials() != null ? section.getMaterials().stream()
                        .map(this.materialDtoMapper::convertToDto)
                        .collect(Collectors.toList()) : null,
                section.getMaterialRequests() != null ? section.getMaterialRequests().stream()
                        .map(this.materialRequestDtoMapper::convertToDto)
                        .collect(Collectors.toList()) : null
        );
    }
}
