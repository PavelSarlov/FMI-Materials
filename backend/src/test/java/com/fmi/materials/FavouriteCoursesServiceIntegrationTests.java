package com.fmi.materials;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.transaction.Transactional;

import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.exception.EntityAlreadyExistsException;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.exception.InvalidArgumentException;
import com.fmi.materials.model.User;
import com.fmi.materials.service.CourseService;
import com.fmi.materials.service.FavouriteCoursesService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FavouriteCoursesServiceIntegrationTests {

    private final FavouriteCoursesService favouriteCoursesService;
    private final CourseService coursesService;

    @BeforeEach
    public void setup() {
        User userDetails = new User(1L, "U1", "pass", "u1@email.com");

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
    }

    @Test
    @Transactional
    public void whenGetFavouriteCoursesWithValidUserId_thenReturnListWithCourseDtoWithId() {
        this.favouriteCoursesService.addCourse(1L, 1L);
        List<CourseDtoWithId> found = this.favouriteCoursesService.getFavouriteCourses(1L);

        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0).getName()).isEqualTo("Web Development with Java");
    }

    @Test
    @Transactional
    public void whenGetFavouriteCoursesWithInvalidUserId_thenThrowEntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> this.favouriteCoursesService.getFavouriteCourses(0L));
        assertThat(exception.getMessage()).isEqualTo("User with id = '0' not found");
    }

    @Test
    @Transactional
    public void whenDeleteFavouriteCourseWithValidUserIdAndCourseId_thenListSizeIsOneLess() {
        this.favouriteCoursesService.addCourse(1L, 1L);

        Integer sizeBeforeDeletion = this.favouriteCoursesService.getFavouriteCourses(1L).size();
        this.favouriteCoursesService.deleteFavouriteCourse(1L, 1L);
        Integer sizeAfterDeletion = this.favouriteCoursesService.getFavouriteCourses(1L).size();

        assertThat(sizeAfterDeletion + 1).isEqualTo(sizeBeforeDeletion);
    }

    @Test
    @Transactional
    @WithMockUser(username = "u1@email.com", password = "pass", authorities = "USER")
    public void whenDeleteFavouriteCourseWithInvalidUserId_thenThrowUnauthorized() {
        Exception exception = assertThrows(InvalidArgumentException.class,
                () -> this.favouriteCoursesService.deleteFavouriteCourse(0L, 1L));
        assertThat(exception.getMessage()).isEqualTo("User authentication failed");
    }

    @Test
    @Transactional
    @WithMockUser(username = "u1@email.com", password = "pass", authorities = "USER")
    public void whenDeleteFavouriteCourseWithInvalidCourseId_thenThrowEntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> this.favouriteCoursesService.deleteFavouriteCourse(1L, 0L));
        assertThat(exception.getMessage()).isEqualTo("Course with id = '0' not found");
    }

    @Test
    @Transactional
    public void whenAddFavouriteCourseWithValidUserIdAndCourseId_thenListSizeIsOneGreater() {
        Integer sizeBeforeAddition = this.favouriteCoursesService.getFavouriteCourses(1L).size();

        this.favouriteCoursesService.addCourse(1L, 1L);
        Integer sizeAfterAddition = this.favouriteCoursesService.getFavouriteCourses(1L).size();

        assertThat(sizeAfterAddition).isEqualTo(sizeBeforeAddition + 1);
    }

    @Test
    @Transactional
    @WithMockUser(username = "u1@email.com", password = "pass", authorities = "USER")
    public void whenAddFavouriteCourseWithInvalidUserId_thenThrowUnauthorized() {
        Exception exception = assertThrows(InvalidArgumentException.class,
                () -> this.favouriteCoursesService.deleteFavouriteCourse(0L, 1L));
        assertThat(exception.getMessage()).isEqualTo("User authentication failed");
    }

    @Test
    @Transactional
    public void whenAddFavouriteCourseWithInvalidCourseId_thenThrowEntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> this.favouriteCoursesService.addCourse(1L, 0L));
        assertThat(exception.getMessage()).isEqualTo("Course with id = '0' not found");
    }

    @Test
    @Transactional
    public void whenAddFavouriteCourseWithValidCourseId_thenThrowntityAlreadyExistsException() {
        Exception exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            this.favouriteCoursesService.addCourse(1L, 1L);
            this.favouriteCoursesService.addCourse(1L, 1L);
        });
        assertThat(exception.getMessage()).isEqualTo("Course with id = '1' already exists");
    }
}
