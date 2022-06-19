package com.fmi.materials.service;

import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.response.ResponseDto;

import java.io.IOException;

public interface MaterialService {

    MaterialDto createMaterial(MaterialDto materialDto, Long sectionid) throws IOException;

    ResponseDto deleteMaterial(Long materialId);

    MaterialDtoWithData findMaterialById(Long materialId);

    MaterialDtoWithData findCourseMaterialByName(Long courseId, String name);
}
