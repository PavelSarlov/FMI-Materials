package com.fmi.materials.mapper;

import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.model.Material;
import org.springframework.stereotype.Component;

@Component
public class MaterialDtoMapper {
    public Material convertToEntity(MaterialDto materialDto) {
        if (materialDto == null) {
            return null;
        }

        return new Material(
                materialDto.getId(),
                materialDto.getFileFormat(),
                materialDto.getFileName(),
                materialDto instanceof MaterialDtoWithData ? ((MaterialDtoWithData) materialDto).getData() : null,
                null);
    }

    public MaterialDto convertToDto(Material material) {
        if (material == null) {
            return null;
        }

        return new MaterialDto(
                material.getId(),
                material.getFileFormat(),
                material.getFileName());
    }

    public MaterialDtoWithData convertToDtoWithData(Material material) {
        if (material == null) {
            return null;
        }

        return new MaterialDtoWithData(
                material.getId(),
                material.getFileFormat(),
                material.getFileName(),
                material.getData());
    }
}
