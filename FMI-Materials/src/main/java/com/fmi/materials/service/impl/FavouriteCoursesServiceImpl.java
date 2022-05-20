package com.fmi.materials.service.impl;

import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.exception.InvalidArgumentException;
import com.fmi.materials.mapper.CourseDtoMapper;
import com.fmi.materials.mapper.UserDtoMapper;
import com.fmi.materials.model.Course;
import com.fmi.materials.model.CustomUserDetails;
import com.fmi.materials.model.User;
import com.fmi.materials.repository.CourseRepository;
import com.fmi.materials.service.FavouriteCoursesService;
import com.fmi.materials.service.UserService;
import com.fmi.materials.vo.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

public class FavouriteCoursesServiceImpl implements FavouriteCoursesService {
    private CourseRepository courseRepository;
    private UserService userService;
    private UserDtoMapper userDtoMapper;
    private CourseDtoMapper courseDtoMapper;

    @Autowired
    public FavouriteCoursesServiceImpl(
            CourseRepository courseRepository,
            UserService userService,
            UserDtoMapper userDtoMapper,
            CourseDtoMapper courseDtoMapper
    ) {
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
        this.courseDtoMapper = courseDtoMapper;
    }

    @Override
    public List<CourseDtoWithId> getFavouriteCourses(Long userId) {
        User user = this.userDtoMapper.convertToEntityWithId(this.userService.findUserById(userId));

        return user.getFavouriteCourses().stream()
                .map(this.courseDtoMapper::convertToDtoWithId)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFavouriteCourse(Long userId, Long courseId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
            Long loggedUserId = userDetails.getId();

            if(loggedUserId != userId) {
                throw new InvalidArgumentException(ExceptionMessage.INVALID_OPERATION.getFormattedMessage());
            }

            User user = this.userDtoMapper.convertToEntityWithId(this.userService.findUserById(userId));
            Course course = this.courseRepository.findById(courseId)
                    .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId)));

            user.removeCourse(course);
            course.removeUser(user);

            this.courseRepository.save(course);
            this.userService.updateUser(this.userDtoMapper.convertToDtoWithId(user));
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<CourseDtoWithId> addCourse(Long userId, Long courseId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
            Long loggedUserId = userDetails.getId();

            if(loggedUserId != userId) {
                throw new InvalidArgumentException(ExceptionMessage.INVALID_OPERATION.getFormattedMessage());
            }

            User user = this.userDtoMapper.convertToEntityWithId(this.userService.findUserById(userId));
            Course course = this.courseRepository.findById(courseId)
                    .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId)));

            user.addCourse(course);
            course.addUser(user);

            this.courseRepository.save(course);
            UserDto userDto = this.userService.updateUser(this.userDtoMapper.convertToDtoWithId(user));
            return userDto.getFavouriteCourses();
        } catch (Exception e) {
            throw e;
        }
    }
}
