package com.fmi.materials;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.transaction.Transactional;

import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.service.FacultyDepartmentService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FacultyDepartmentServiceIntegrationTests {

    private final FacultyDepartmentService facultyDepartmentService;

    @Test
    @Transactional
    public void whenCreateDepartment_thenReturnCreatedDepartment() {
        FacultyDepartmentDto facultyDepartmentDto = new FacultyDepartmentDto(null, "Created");

        FacultyDepartmentDto created = this.facultyDepartmentService.createFacultyDepartment(facultyDepartmentDto);

        assertThat(created.getName()).isEqualTo("Created");
    }

    @Test
    @Transactional
    public void whenDeleteDepartment_thenDepartmentIsDeleted() {
        FacultyDepartmentDto facultyDepartmentDto = new FacultyDepartmentDto(null, "ToDelete");

        FacultyDepartmentDto toDelete = this.facultyDepartmentService.createFacultyDepartment(facultyDepartmentDto);

        this.facultyDepartmentService.deleteFacultyDepartment(toDelete.getId());

        assertThrows(EntityNotFoundException.class,
                () -> this.facultyDepartmentService.deleteFacultyDepartment(toDelete.getId()));
    }

    @Test
    @Transactional
    public void whenUpdateDepartment_thenReturnUpdatedDepartment() {
        FacultyDepartmentDto facultyDepartmentDto = new FacultyDepartmentDto(null, "ToUpdate");

        FacultyDepartmentDto toUpdate = this.facultyDepartmentService.createFacultyDepartment(facultyDepartmentDto);
        toUpdate.setName("Updated");

        FacultyDepartmentDto updated = this.facultyDepartmentService.updateFacultyDepartment(toUpdate);

        assertThat(updated.getName()).isEqualTo("Updated");
    }

    @Test
    @Transactional
    public void whenFindDepartmentById_thenReturnFoundDepartment() {
        FacultyDepartmentDto facultyDepartmentDto = new FacultyDepartmentDto(null, "ToFind");

        FacultyDepartmentDto toFind = this.facultyDepartmentService.createFacultyDepartment(facultyDepartmentDto);
        FacultyDepartmentDto found = this.facultyDepartmentService.findById(toFind.getId());

        assertThat(found.getId()).isEqualTo(toFind.getId());
    }

    @Test
    @Transactional
    public void whenFindAllDepartments_thenReturnListOfAllDepartments() {
        List<FacultyDepartmentDto> departments = this.facultyDepartmentService.findAllFacultyDepartments();

        assertThat(departments.size()).isNotEqualTo(0);
    }
}
