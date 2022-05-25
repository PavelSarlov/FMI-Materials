package com.fmi.materials.service.impl;

import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.materialrequest.MaterialRequestDto;
import com.fmi.materials.dto.materialrequest.MaterialRequestDtoWithData;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.exception.InvalidArgumentException;
import com.fmi.materials.mapper.MaterialDtoMapper;
import com.fmi.materials.mapper.MaterialRequestDtoMapper;
import com.fmi.materials.model.*;
import com.fmi.materials.repository.MaterialRequestRepository;
import com.fmi.materials.repository.SectionRepository;
import com.fmi.materials.repository.UserRepository;
import com.fmi.materials.service.CourseService;
import com.fmi.materials.service.MaterialRequestService;
import com.fmi.materials.vo.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
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

    private Boolean authenticateCurrentUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Long loggedUserId = userDetails.getId();

        if(loggedUserId != userId) {
            return false;
        }
        return true;
    }

    private void addMaterial(MaterialRequest materialRequest) throws IOException {
        MultipartFile multipartFile = new MockMultipartFile(materialRequest.getFileName(),
                materialRequest.getFileName(),
                materialRequest.getFileFormat(),
                materialRequest.getData());
        this.courseService.createMaterial(multipartFile, materialRequest.getSection().getId());
    }

    @Override
    public List<MaterialRequestDto> getAllUserMaterialRequests(Long userId) {
        if (!authenticateCurrentUser(userId)) {
            throw new InvalidArgumentException(ExceptionMessage.INVALID_OPERATION.getFormattedMessage());
        }

        return this.materialRequestRepository.findAllByUserId(userId).stream()
                .map(this.materialRequestDtoMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MaterialRequestDto getMaterialRequest(Long userId, Long materialRequestId) {
        if (!authenticateCurrentUser(userId)) {
            throw new InvalidArgumentException(ExceptionMessage.INVALID_OPERATION.getFormattedMessage());
        }

        return this.materialRequestDtoMapper.convertToDto(
                this.materialRequestRepository.findByIdAndUserId(materialRequestId, userId)
                        .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Request", "id", materialRequestId))));
    }

    @Override
    public MaterialDtoWithData getMaterialFromMaterialRequest(Long userId, Long materialRequestId) {
        if (!authenticateCurrentUser(userId)) {
            throw new InvalidArgumentException(ExceptionMessage.INVALID_OPERATION.getFormattedMessage());
        }

        MaterialRequest materialRequest = this.materialRequestRepository.findByIdAndUserId(materialRequestId, userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Request", "id", materialRequestId)));

        Material material = new Material(materialRequest.getFileFormat(), materialRequest.getFileName(), materialRequest.getData(), null);
        return this.materialDtoMapper.convertToDtoWithData(material);
    }

    @Override
    public void processRequest(Long userId, Long materialRequestId, Boolean status) throws IOException {
        if (!authenticateCurrentUser(userId)) {
            throw new InvalidArgumentException(ExceptionMessage.INVALID_OPERATION.getFormattedMessage());
        }

        MaterialRequest materialRequest = this.materialRequestRepository.findByIdAndUserId(materialRequestId, userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Request", "id", materialRequestId)));

        if (status == true) {
            this.addMaterial(materialRequest);
        }

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId)));
        Long sectionId = materialRequest.getSection().getId();
        Section section = this.sectionRepository.findById(sectionId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Section", "id", sectionId)));

        user.removeMaterialRequest(materialRequest);
        section.removeMaterialRequest(materialRequest);

        this.userRepository.save(user);
        this.sectionRepository.save(section);
    }
}
