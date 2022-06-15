package com.fmi.materials;

import com.fmi.materials.mapper.CourseDtoMapper;
import com.fmi.materials.model.Course;
import com.fmi.materials.model.FacultyDepartment;
import com.fmi.materials.repository.CourseRepository;
import com.fmi.materials.service.CourseService;
import com.fmi.materials.vo.CourseGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CourseServiceUnitTests {

    @Autowired
    private CourseService courseService;
    @MockBean
    private CourseRepository courseRepository;
    @Autowired
    private CourseDtoMapper courseDtoMapper;

    @BeforeEach
    public void setup() {
        Course course = new Course(1L, "Web Dontevelopment with Java", "Spring Boot, MVC", "Admin Adminchev", new FacultyDepartment(1L, "Software Technologies"), CourseGroup.CSC);

        Mockito.when(this.courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        Mockito.when(this.courseRepository.save(course)).thenReturn(course);
    }

    @Test
    public void whenFindById_thenReturnCourse() {
        Optional<Course> found = this.courseRepository.findById(1L);

        assertThat(found.isPresent()).isEqualTo(true);
    }
}
