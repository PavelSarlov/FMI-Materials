package com.fmi.materials.mapper;

import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.model.Material;
import com.fmi.materials.model.Section;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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

    public Material convertToEntity(String fileFormat, String fileName, byte[] data, Section section) throws IOException {
        return new Material(
                fileFormat,
                fileName,
                data,
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

    public MaterialDtoWithData convertToDtoWithData(Material material) {
        return new MaterialDtoWithData(
                material.getId(),
                material.getFileFormat(),
                material.getFileName(),
                material.getData()
        );
    }
}
