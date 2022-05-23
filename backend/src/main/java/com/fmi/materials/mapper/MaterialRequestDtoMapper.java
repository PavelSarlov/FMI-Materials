package com.fmi.materials.mapper;

import com.fmi.materials.dto.materialrequest.MaterialRequestDto;
import com.fmi.materials.dto.materialrequest.MaterialRequestDtoWithData;
import com.fmi.materials.model.MaterialRequest;
import com.fmi.materials.model.Section;
import com.fmi.materials.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MaterialRequestDtoMapper {
    @Autowired
    private SectionDtoMapper sectionDtoMapper;

    public MaterialRequest convertToEntity(MaterialRequestDto materialDto) {
        return new MaterialRequest(
                materialDto.getId(),
                materialDto.getFileFormat(),
                materialDto.getFileName(),
                null,
                null,
                this.sectionDtoMapper.convertToEntity(materialDto.getSectionDto())
        );
    }

    public MaterialRequest convertToEntity(MultipartFile materialFile, User user, Section section) throws IOException {
        return new MaterialRequest(
                materialFile.getContentType(),
                materialFile.getOriginalFilename(),
                materialFile.getBytes(),
                user,
                section
        );
    }

    public MaterialRequestDto convertToDto(MaterialRequest material) {
        return new MaterialRequestDto(
                material.getId(),
                material.getFileFormat(),
                material.getFileName(),
                this.sectionDtoMapper.convertToDto(material.getSection())
        );
    }

    public MaterialRequestDtoWithData convertToDtoWithData(MaterialRequest material) {
        return new MaterialRequestDtoWithData(
                material.getId(),
                material.getFileFormat(),
                material.getFileName(),
                this.sectionDtoMapper.convertToDto(material.getSection()),
                material.getData()
        );
    }
}
