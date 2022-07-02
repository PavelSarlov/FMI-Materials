package com.fmi.materials.service.impl;

import java.io.IOException;

import javax.transaction.Transactional;

import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.response.ResponseDtoSuccess;
import com.fmi.materials.exception.EntityAlreadyExistsException;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.mapper.MaterialDtoMapper;
import com.fmi.materials.mapper.SectionDtoMapper;
import com.fmi.materials.model.Material;
import com.fmi.materials.repository.MaterialRepository;
import com.fmi.materials.service.MaterialService;
import com.fmi.materials.service.SectionService;
import com.fmi.materials.vo.ExceptionMessage;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialDtoMapper materialDtoMapper;
    private final SectionDtoMapper sectionDtoMapper;
    private final SectionService sectionService;

    @Override
    @Transactional
    public MaterialDto createMaterial(MaterialDto materialDto, Long sectionId) throws IOException {
        if (this.materialRepository.findByName(materialDto.getFileName(), sectionId).isPresent()) {
            throw new EntityAlreadyExistsException(ExceptionMessage.ALREADY_EXISTS.getFormattedMessage("Material",
                    "filename", materialDto.getFileName()));
        }

        Material material = this.materialDtoMapper.convertToEntity(materialDto);
        material.setSection(this.sectionDtoMapper.convertToEntity(this.sectionService.findSectionById(sectionId)));
        material.setData(((MaterialDtoWithData) materialDto).getData());

        return this.materialDtoMapper.convertToDto(this.materialRepository.save(material));
    }

    @Override
    @Transactional
    public ResponseDto deleteMaterial(Long materialId) {
        if (!this.materialRepository.existsById(materialId)) {
            throw new EntityNotFoundException(
                    ExceptionMessage.NOT_FOUND.getFormattedMessage("Material", "id", materialId));
        }
        this.materialRepository.deleteById(materialId);

        return new ResponseDtoSuccess(HttpStatus.OK,
                String.format("Material with id = '%s' deleted successfully", materialId));
    }

    @Override
    @Transactional
    public MaterialDtoWithData findMaterialById(Long materialId) {
        return this.materialDtoMapper.convertToDtoWithData(this.materialRepository.findById(materialId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("Material", "id", materialId))));
    }

    @Override
    @Transactional
    public MaterialDtoWithData findCourseMaterialByName(Long sectionId, String name) {
        return this.materialDtoMapper.convertToDtoWithData(this.materialRepository.findByName(name, sectionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("Material", "name", name))));
    }
}
