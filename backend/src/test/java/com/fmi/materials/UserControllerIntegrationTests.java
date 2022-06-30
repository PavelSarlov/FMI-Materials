package com.fmi.materials;

import com.fmi.materials.mapper.CourseDtoMapper;
import com.fmi.materials.model.User;
import com.fmi.materials.repository.CourseRepository;
import com.fmi.materials.repository.UserRepository;
import com.fmi.materials.service.FavouriteCoursesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerIntegrationTests {
    @Autowired
    private FavouriteCoursesService favouriteCoursesService;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseDtoMapper courseDtoMapper;
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();

        User userDetails = new User(1L,"U1", "pass", "u1@email.com");

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
    }

    @Test
    @Transactional
    public void whenDeleteFavouriteCourseWithValidUserIdAndCourseId_thenListSizeIsOneLess() throws Exception {
        Integer sizeBeforeDeletion = this.favouriteCoursesService.getFavouriteCourses(1L).size();

        this.mockMvc.perform(delete("/api/users/1/favourite-courses/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        Integer sizeAfterDeletion = this.favouriteCoursesService.getFavouriteCourses(1L).size();

        assertThat(sizeAfterDeletion+1).isEqualTo(sizeBeforeDeletion);
    }

    @Test
    @Transactional
    @WithMockUser(username = "u1@email.com", password = "pass", authorities = "USER")
    public void whenDeleteFavouriteCourseWithInvalidUserId_thenThrowUnauthorized() throws Exception {
        this.mockMvc.perform(delete("/api/users/0/favourite-courses/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isBadRequest(), jsonPath("$.error").value("User authentication failed"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "u1@email.com", password = "pass", authorities = "USER")
    public void whenDeleteFavouriteCourseWithInvalidCourseId_thenThrowEntityNotFoundException() throws Exception {
        this.mockMvc.perform(delete("/api/users/1/favourite-courses/0").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isNotFound(), jsonPath("$.error").value("Course with id = \'0\' not found"));
    }
}
