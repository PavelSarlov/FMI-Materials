package com.fmi.materials.service.impl;

import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.materialrequest.MaterialRequestDto;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.mapper.MaterialDtoMapper;
import com.fmi.materials.mapper.MaterialRequestDtoMapper;
import com.fmi.materials.model.Material;
import com.fmi.materials.model.MaterialRequest;
import com.fmi.materials.repository.MaterialRequestRepository;
import com.fmi.materials.repository.SectionRepository;
import com.fmi.materials.repository.UserRepository;
import com.fmi.materials.service.CourseService;
import com.fmi.materials.service.MaterialRequestService;
import com.fmi.materials.util.CustomUtils;
import com.fmi.materials.vo.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialRequestServiceImpl implements MaterialRequestService {
    private MaterialRequestRepository materialRequestRepository;
    private MaterialRequestDtoMapper materialRequestDtoMapper;
    private MaterialDtoMapper materialDtoMapper;
    private SectionRepository sectionRepository;
    private UserRepository userRepository;
    private CourseService courseService;

    @Autowired
    public MaterialRequestServiceImpl(MaterialRequestRepository materialRequestRepository,
            MaterialRequestDtoMapper materialRequestDtoMapper,
            MaterialDtoMapper materialDtoMapper,
            SectionRepository sectionRepository,
            UserRepository userRepository,
            CourseService courseService) {
        this.materialRequestRepository = materialRequestRepository;
        this.materialRequestDtoMapper = materialRequestDtoMapper;
        this.materialDtoMapper = materialDtoMapper;
        this.sectionRepository = sectionRepository;
        this.userRepository = userRepository;
        this.courseService = courseService;
    }

    @Override
    public List<MaterialRequestDto> getAllUserMaterialRequests(Long userId) {
        CustomUtils.authenticateCurrentUser(userId);

        return this.materialRequestRepository.findAllByUserId(userId).stream()
                .map(r -> this.materialRequestDtoMapper.convertToDtoWithCourseId(r, r.getSection().getCourse().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<MaterialRequestDto> getAllAdminMaterialRequests(Long adminId) {
        CustomUtils.authenticateCurrentUser(adminId);

        return this.materialRequestRepository.findAllByAdminId(adminId).stream()
                .map(r -> this.materialRequestDtoMapper.convertToDtoWithCourseId(r, r.getSection().getCourse().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public MaterialRequestDto getMaterialRequestById(Long userId, Long materialRequestId) {
        CustomUtils.authenticateCurrentUser(userId);

        return this.materialRequestDtoMapper
                .convertToDto(this.materialRequestRepository.findByIdAndAdminId(materialRequestId, userId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                ExceptionMessage.NOT_FOUND.getFormattedMessage("Request", "id", materialRequestId))));
    }

    @Override
    public MaterialDtoWithData getMaterialFromMaterialRequest(Long userId, Long materialRequestId) {
        CustomUtils.authenticateCurrentUser(userId);

        MaterialRequest materialRequest = this.materialRequestRepository.findByIdAndAdminId(materialRequestId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("Request", "id", materialRequestId)));

        Material material = new Material(materialRequest.getFileFormat(), materialRequest.getFileName(),
                materialRequest.getData(), null);

        return this.materialDtoMapper.convertToDtoWithData(material);
    }

    @Override
    public void processRequest(Long userId, Long materialRequestId, Boolean status) throws IOException {
        CustomUtils.authenticateCurrentUser(userId);

        MaterialRequest materialRequest = this.materialRequestRepository.findByIdAndAdminId(materialRequestId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("Request", "id", materialRequestId)));

        if (status) {
            this.courseService.createMaterial(this.materialRequestDtoMapper.convertToMaterialDto(materialRequest),
                    materialRequest.getSection().getId());
        }

        this.materialRequestRepository.deleteById(materialRequestId);
    }
}
