package com.fmi.materials.service;

import java.io.IOException;

import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.response.ResponseDto;

public interface MaterialService {

    MaterialDto createMaterial(MaterialDto materialDto, Long sectionid) throws IOException;

    ResponseDto deleteMaterial(Long materialId);

    MaterialDtoWithData findMaterialById(Long materialId);

    MaterialDtoWithData findCourseMaterialByName(Long sectionId, String name);
}
