package com.fmi.materials.service;

import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.materialrequest.MaterialRequestDto;

import java.io.IOException;
import java.util.List;

public interface MaterialRequestService {
    List<MaterialRequestDto> getAllUserMaterialRequests(Long userId);

    List<MaterialRequestDto> getAllAdminMaterialRequests(Long adminId);

    MaterialRequestDto getMaterialRequestById(Long userId, Long materialRequestId);

    MaterialDtoWithData getMaterialFromMaterialRequest(Long userId, Long materialRequestId);

    void processRequest(Long userId, Long materialRequestId, Boolean status) throws IOException;
}
