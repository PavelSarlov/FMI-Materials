package com.fmi.materials;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.transaction.Transactional;

import com.fmi.materials.dto.course.CourseDto;
import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.dto.pagedresult.PagedResultDto;
import com.fmi.materials.dto.section.SectionDto;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.service.CourseService;
import com.fmi.materials.vo.CourseGroup;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseServiceIntegrationTests {

    private final CourseService courseService;

    @Test
    @Transactional
    public void whenFindById_thenReturnCourseDto() {
        CourseDto found = this.courseService.findById(1L);

        assertThat(found.getName()).isEqualTo("Web Development with Java");
    }

    @Test
    @Transactional
    public void whenCreateCourse_thenReturnCreatedCourseDto() {
        CourseDto toCreate = new CourseDto("Some new course", "asd", "myself", new FacultyDepartmentDto(1L, "asd"),
                CourseGroup.AM, null);

        CourseDtoWithId created = (CourseDtoWithId) this.courseService.createCourse(toCreate);

        assertThat(created.getName()).isEqualTo("Some new course");
        assertThat(created.getFacultyDepartmentDto().getName()).isEqualTo("Algebra");
    }

    @Test
    @Transactional
    public void whenDeleteNonExistantId_thenThrowEntityNotFound() {
        Exception ex = assertThrows(EntityNotFoundException.class, () -> this.courseService.deleteCourse(0L));
        assertThat(ex.getMessage()).isEqualTo("Course with id = '0' not found");
    }

    @Test
    @Transactional
    public void whenUpdateCourse_thenReturnUpdatedCourse() {
        CourseDtoWithId toUpdate = new CourseDtoWithId(1L, "asd", null, null, null, null, null);

        CourseDtoWithId updated = (CourseDtoWithId) this.courseService.updateCourse(toUpdate);

        assertThat(updated.getName()).isEqualTo("asd");
        assertThat(updated.getDescription()).isNull();
        assertThat(updated.getFacultyDepartmentDto()).isNull();
        assertThat(updated.getCourseGroup()).isNull();
    }

    @Test
    @Transactional
    public void whenFindCourses_thenReturnPageableDtoWithCourses() {
        String filter = "name";
        String filterValue = "";
        int page = 0;
        int size = 10;
        String sortBy = "name";
        Boolean desc = false;

        Sort sort = Sort.by((sortBy != null && sortBy != "" ? sortBy : "name"));
        if (desc != null && desc)
            sort = sort.descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        PagedResultDto<CourseDto> pagedResultDto = this.courseService.findCourses(filter, filterValue, pageable);

        assertThat(pagedResultDto).isNotNull();
        assertThat(pagedResultDto.getCurrentPage()).isEqualTo(0);
        assertThat(pagedResultDto.getItems().stream().count()).isNotEqualTo(0);
    }

    @Test
    @Transactional
    public void whenFindCourseSections_thenReturnCourseSectionDtos() {
        List<SectionDto> sections = this.courseService.findAllCourseSections(1L);

        assertThat(sections.size()).isNotEqualTo(0);
    }
}
