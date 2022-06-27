package com.fmi.materials;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import javax.transaction.Transactional;

import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.mapper.MaterialDtoMapper;
import com.fmi.materials.repository.MaterialRepository;
import com.fmi.materials.service.MaterialService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MaterialServiceIntegrationTests {

    @Autowired
    private MaterialService materialService;
    @Autowired
    private MaterialDtoMapper materialDtoMapper;
    @Autowired
    private MaterialRepository materialRepository;

    @Test
    @Transactional
    public void whenCreateMaterial_thenReturnCreatedMaterial() throws IOException {
        MaterialDto materialDto = new MaterialDtoWithData(null, "text/plain", "created.txt", new byte[] { 0 });

        MaterialDto created = this.materialService.createMaterial(materialDto, 1L);

        assertThat(created.getFileName()).isEqualTo("created.txt");
    }

    @Test
    @Transactional
    public void whenDeleteMaterial_thenMaterialIsDeleted() throws IOException {
        MaterialDto materialDto = new MaterialDtoWithData(null, "text/plain", "created.txt", new byte[] { 0 });

        MaterialDto created = this.materialService.createMaterial(materialDto, 1L);

        this.materialService.deleteMaterial(created.getId());

        assertThrows(EntityNotFoundException.class, () -> this.materialService.deleteMaterial(created.getId()));
    }

    @Test
    @Transactional
    public void whenFindMaterialById_thenReturnFoundMaterial() throws IOException {
        MaterialDto materialDto = new MaterialDtoWithData(null, "text/plain", "tofind.txt", new byte[] { 0 });

        MaterialDto toFind = this.materialService.createMaterial(materialDto, 1L);

        MaterialDto found = this.materialService.findMaterialById(toFind.getId());

        assertThat(found.getId()).isEqualTo(toFind.getId());
    }

    @Test
    @Transactional
    public void whenFindMaterialByNameAndSectionId_thenReturnFoundMaterial() throws IOException {
        MaterialDto materialDto = new MaterialDtoWithData(null, "text/plain", "tofind.txt", new byte[] { 0 });

        this.materialService.createMaterial(materialDto, 1L);

        MaterialDto found = this.materialService.findCourseMaterialByName(1L, "tofind.txt");

        assertThat(found.getFileName()).isEqualTo("tofind.txt");
    }
}
