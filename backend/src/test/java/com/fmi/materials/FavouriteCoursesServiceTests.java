package com.fmi.materials;

import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.mapper.CourseDtoMapper;
import com.fmi.materials.repository.CourseRepository;
import com.fmi.materials.repository.UserRepository;
import com.fmi.materials.service.FavouriteCoursesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class FavouriteCoursesServiceTests {

    @Autowired
    private FavouriteCoursesService favouriteCoursesService;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseDtoMapper courseDtoMapper;

    @Test
    @Transactional
    public void whenGetFavouriteCoursesWithValidUserId_thenReturnListWithCourseDtoWithId() {
        List<CourseDtoWithId> found = this.favouriteCoursesService.getFavouriteCourses(1L);

        assertThat(found.size()==1);
        assertThat(found.get(0).getName()).isEqualTo("Web Development with Java");
    }

    @Test
    @Transactional
    public void whenGetFavouriteCoursesWithInvalidUserId_thenThrowEntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, () -> this.favouriteCoursesService.getFavouriteCourses(0L));
        assertThat(exception.getMessage()).isEqualTo("User with id = '0' not found");
    }

    @Test
    @Transactional
    @WithMockUser(username = "u1@email.com", password = "pass", roles = "USER")
    public void whenDeleteFavouriteCourseWithValidUserIdAndCourseId_thenListSizeIsOneLess() {
        Integer sizeBeforeDeletion = this.favouriteCoursesService.getFavouriteCourses(1L).size();

        this.favouriteCoursesService.deleteFavouriteCourse(1L, 1L);
        Integer sizeAfterDeletion = this.favouriteCoursesService.getFavouriteCourses(1L).size();

        assertThat(sizeAfterDeletion+1==sizeBeforeDeletion);
    }

    @Test
    @Transactional
    public void whenDeleteFavouriteCourseWithInvalidUserId_thenThrowEntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, () ->  this.favouriteCoursesService.deleteFavouriteCourse(0L, 1L));
        assertThat(exception.getMessage()).isEqualTo("User with id = '0' not found");
    }

    @Test
    @Transactional
    public void whenDeleteFavouriteCourseWithInvalidCourseId_thenThrowEntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, () ->  this.favouriteCoursesService.deleteFavouriteCourse(1L, 0L));
        assertThat(exception.getMessage()).isEqualTo("Course with id = '0' not found");
    }
}