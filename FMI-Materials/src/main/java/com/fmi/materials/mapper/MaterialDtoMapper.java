package com.fmi.materials.mapper;

import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.model.Material;
import org.springframework.stereotype.Component;

@Component
public class MaterialDtoMapper {
    public Material convertToEntity(MaterialDto materialDto) {
        return new Material(
                materialDto.getId(),
                materialDto.getFileFormat(),
                materialDto.getFileName(),
                null,
                null
        );
    }

    public MaterialDto convertToDto(Material material) {
        return new MaterialDto(
                material.getId(),
                material.getFileFormat(),
                material.getFileName()
        );
    }
}
