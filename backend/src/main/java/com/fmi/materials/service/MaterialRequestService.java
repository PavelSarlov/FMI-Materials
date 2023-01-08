package com.fmi.materials.service;

import java.io.IOException;
import java.util.List;

import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.materialrequest.MaterialRequestDto;

public interface MaterialRequestService {
    List<MaterialRequestDto> getAllAdminMaterialRequests(Long adminId);

    List<MaterialRequestDto> getAllMaterialRequests(Long adminId);

    MaterialRequestDto getMaterialRequestById(Long userId, Long materialRequestId);

    MaterialDtoWithData getMaterialFromMaterialRequest(Long userId, Long materialRequestId);

    void processRequest(Long userId, Long materialRequestId, Boolean status) throws IOException;
}
