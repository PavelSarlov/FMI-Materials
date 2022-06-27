package com.fmi.materials;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import com.fmi.materials.dto.section.SectionDto;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.mapper.SectionDtoMapper;
import com.fmi.materials.model.Section;
import com.fmi.materials.repository.SectionRepository;
import com.fmi.materials.service.SectionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SectionServiceIntegrationTests {

    @Autowired
    private SectionService sectionService;
    @Autowired
    private SectionDtoMapper sectionDtoMapper;
    @Autowired
    private SectionRepository sectionRepository;

    @Test
    @Transactional
    public void whenCreateSection_thenReturnCreatedSection() {
        SectionDto sectionDto = new SectionDto(null, "Created", null, null);

        SectionDto created = this.sectionService.createSection(sectionDto, 1L);

        assertThat(created.getName()).isEqualTo("Created");
    }

    @Test
    @Transactional
    public void whenDeleteSection_thenSectionIsDeleted() {
        List<Section> sections = this.sectionRepository.findAll();
        Long last = sections.stream()
                .max(Comparator.comparing(s -> s.getId())).get().getId();
        this.sectionService.deleteSection(last);

        assertThrows(EntityNotFoundException.class, () -> this.sectionService.deleteSection(last));
    }

    @Test
    @Transactional
    public void whenPatchSection_thenReturnPatchedSection() {
        SectionDto sectionDto = new SectionDto(1L, "Patched", null, null);

        var wrapper = new Object() {
            SectionDto patched = null;
        };
        assertDoesNotThrow(() -> wrapper.patched = this.sectionService.patchSection(sectionDto));

        assertThat(wrapper.patched).isNotNull();
        assertThat(wrapper.patched.getName()).isEqualTo("Patched");
    }

    @Test
    @Transactional
    public void whenFindSectionById_thenReturnFoundSection() {
        SectionDto found = this.sectionService.findSectionById(1L);

        assertThat(found.getId()).isEqualTo(1L);
    }
}
