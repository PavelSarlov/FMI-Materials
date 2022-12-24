package com.fmi.materials;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import javax.transaction.Transactional;

import com.fmi.materials.dto.material.MaterialDto;
import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.section.SectionDto;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.service.MaterialService;
import com.fmi.materials.service.SectionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MaterialServiceIntegrationTests {

    private final MaterialService materialService;
    private final SectionService sectionService;

    @Test
    @Transactional
    public void whenCreateMaterial_thenReturnCreatedMaterial() throws IOException {
        SectionDto sectionDto = new SectionDto(null, "section", null, null);
        MaterialDto materialDto = new MaterialDtoWithData(null, "text/plain", "created.txt", new byte[] { 0 });

        SectionDto createdSection = this.sectionService.createSection(sectionDto, 1L);
        MaterialDto createdMaterial = this.materialService.createMaterial(materialDto, createdSection.getId());

        assertThat(createdMaterial.getFileName()).isEqualTo("created.txt");
    }

    @Test
    @Transactional
    public void whenDeleteMaterial_thenMaterialIsDeleted() throws IOException {
        SectionDto sectionDto = new SectionDto(null, "section", null, null);
        MaterialDto materialDto = new MaterialDtoWithData(null, "text/plain", "created.txt", new byte[] { 0 });

        SectionDto createdSection = this.sectionService.createSection(sectionDto, 1L);
        MaterialDto createdMaterial = this.materialService.createMaterial(materialDto, createdSection.getId());

        this.materialService.deleteMaterial(createdMaterial.getId());

        assertThrows(EntityNotFoundException.class, () -> this.materialService.deleteMaterial(createdMaterial.getId()));
    }

    @Test
    @Transactional
    public void whenFindMaterialById_thenReturnFoundMaterial() throws IOException {
        SectionDto sectionDto = new SectionDto(null, "section", null, null);
        MaterialDto materialDto = new MaterialDtoWithData(null, "text/plain", "tofind.txt", new byte[] { 0 });

        SectionDto createdSection = this.sectionService.createSection(sectionDto, 1L);
        MaterialDto toFind = this.materialService.createMaterial(materialDto, createdSection.getId());

        MaterialDto found = this.materialService.findMaterialById(toFind.getId());

        assertThat(found.getId()).isEqualTo(toFind.getId());
    }

    @Test
    @Transactional
    public void whenFindMaterialByNameAndSectionId_thenReturnFoundMaterial() throws IOException {
        SectionDto sectionDto = new SectionDto(null, "section", null, null);
        MaterialDto materialDto = new MaterialDtoWithData(null, "text/plain", "tofind.txt", new byte[] { 0 });

        SectionDto createdSection = this.sectionService.createSection(sectionDto, 1L);
        this.materialService.createMaterial(materialDto, createdSection.getId());

        MaterialDto found = this.materialService.findCourseMaterialByName(createdSection.getId(), "tofind.txt");

        assertThat(found.getFileName()).isEqualTo("tofind.txt");
    }
}
