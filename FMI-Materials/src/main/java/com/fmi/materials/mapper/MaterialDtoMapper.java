package com.fmi.materials.mapper;

import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.model.Material;
import com.fmi.materials.model.Section;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    public Material convertToEntity(MultipartFile materialFile, Section section) throws IOException {
        return new Material(
                materialFile.getContentType(),
                materialFile.getName(),
                materialFile.getBytes(),
                section
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
