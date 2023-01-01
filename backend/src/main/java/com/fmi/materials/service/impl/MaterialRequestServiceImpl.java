package com.fmi.materials.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.materialrequest.MaterialRequestDto;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.mapper.MaterialDtoMapper;
import com.fmi.materials.mapper.MaterialRequestDtoMapper;
import com.fmi.materials.model.Material;
import com.fmi.materials.model.MaterialRequest;
import com.fmi.materials.repository.MaterialRequestRepository;
import com.fmi.materials.service.MaterialRequestService;
import com.fmi.materials.service.MaterialService;
import com.fmi.materials.util.CustomUtils;
import com.fmi.materials.vo.ExceptionMessage;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaterialRequestServiceImpl implements MaterialRequestService {

    private final MaterialRequestRepository materialRequestRepository;
    private final MaterialRequestDtoMapper materialRequestDtoMapper;
    private final MaterialDtoMapper materialDtoMapper;
    private final MaterialService materialService;

    @Override
    @Transactional
    public List<MaterialRequestDto> getAllAdminMaterialRequests(Long adminId) {
        CustomUtils.authenticateCurrentUser(adminId);

        return this.materialRequestRepository.findAllByAdminId(adminId).stream()
                .map(r -> this.materialRequestDtoMapper.convertToDtoWithCourseId(r, r.getSection().getCourse().getId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<MaterialRequestDto> getAllMaterialRequests(Long adminId) {
        CustomUtils.authenticateCurrentUser(adminId);

        return this.materialRequestRepository.findAll().stream()
                .map(r -> this.materialRequestDtoMapper.convertToDtoWithCourseId(r, r.getSection().getCourse().getId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MaterialRequestDto getMaterialRequestById(Long userId, Long materialRequestId) {
        CustomUtils.authenticateCurrentUser(userId);

        return this.materialRequestDtoMapper
                .convertToDto(this.materialRequestRepository.findById(materialRequestId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                ExceptionMessage.NOT_FOUND.getFormattedMessage("Request", "id", materialRequestId))));
    }

    @Override
    @Transactional
    public MaterialDtoWithData getMaterialFromMaterialRequest(Long userId, Long materialRequestId) {
        CustomUtils.authenticateCurrentUser(userId);

        MaterialRequest materialRequest = this.materialRequestRepository.findById(materialRequestId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("Request", "id", materialRequestId)));

        Material material = new Material(materialRequest.getFileFormat(), materialRequest.getFileName(),
                materialRequest.getData(), null);

        return this.materialDtoMapper.convertToDtoWithData(material);
    }

    @Override
    @Transactional
    public void processRequest(Long userId, Long materialRequestId, Boolean status) throws IOException {
        CustomUtils.authenticateCurrentUser(userId);

        MaterialRequest materialRequest = this.materialRequestRepository.findById(materialRequestId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("Request", "id", materialRequestId)));

        this.materialRequestRepository.deleteById(materialRequestId);

        if (status) {
            this.materialService.createMaterial(this.materialRequestDtoMapper.convertToMaterialDto(materialRequest),
                    materialRequest.getSection().getId());
        }
    }
}
