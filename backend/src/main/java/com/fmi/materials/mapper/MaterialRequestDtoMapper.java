package com.fmi.materials.mapper;

import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.materialrequest.MaterialRequestDto;
import com.fmi.materials.dto.materialrequest.MaterialRequestDtoWithCourseId;
import com.fmi.materials.dto.materialrequest.MaterialRequestDtoWithData;
import com.fmi.materials.model.MaterialRequest;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MaterialRequestDtoMapper {

    private final UserDtoMapper userDtoMapper;

    public MaterialRequest convertToEntity(MaterialRequestDto materialDto) {
        if (materialDto == null) {
            return null;
        }

        return new MaterialRequest(
                materialDto.getId(),
                materialDto.getFileFormat(),
                materialDto.getFileName(),
                materialDto instanceof MaterialRequestDtoWithData ? ((MaterialRequestDtoWithData) materialDto).getData()
                        : null,
                this.userDtoMapper.convertToEntity(materialDto.getUserDto()),
                null);
    }

    public MaterialRequestDto convertToDto(MaterialRequest material) {
        if (material == null) {
            return null;
        }

        return new MaterialRequestDto(
                material.getId(),
                material.getFileFormat(),
                material.getFileName(),
                this.userDtoMapper.convertToDto(material.getUser()),
                material.getSection().getId());
    }

    public MaterialDto convertToMaterialDto(MaterialRequest material) {
        if (material == null) {
            return null;
        }

        return new MaterialDtoWithData(
                material.getId(),
                material.getFileFormat(),
                material.getFileName(),
                material.getData());
    }

    public MaterialRequestDtoWithData convertToDtoWithData(MaterialRequest material) {
        if (material == null) {
            return null;
        }

        return new MaterialRequestDtoWithData(
                material.getId(),
                material.getFileFormat(),
                material.getFileName(),
                this.userDtoMapper.convertToDto(material.getUser()),
                material.getSection().getId(),
                material.getData());
    }

    public MaterialRequestDtoWithCourseId convertToDtoWithCourseId(MaterialRequest material, Long courseId) {
        if (material == null) {
            return null;
        }

        return new MaterialRequestDtoWithCourseId(
                material.getId(),
                material.getFileFormat(),
                material.getFileName(),
                this.userDtoMapper.convertToDto(material.getUser()),
                material.getSection().getId(),
                courseId);
    }
}
