package com.fmi.materials.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.exception.EntityAlreadyExistsException;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.mapper.CourseDtoMapper;
import com.fmi.materials.model.Course;
import com.fmi.materials.model.User;
import com.fmi.materials.repository.CourseRepository;
import com.fmi.materials.repository.FavouriteCoursesRepository;
import com.fmi.materials.repository.UserRepository;
import com.fmi.materials.service.FavouriteCoursesService;
import com.fmi.materials.util.CustomUtils;
import com.fmi.materials.vo.ExceptionMessage;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavouriteCoursesServiceImpl implements FavouriteCoursesService {

    private final CourseRepository courseRepository;
    private final FavouriteCoursesRepository favouriteCoursesRepository;
    private final UserRepository userRepository;
    private final CourseDtoMapper courseDtoMapper;

    @Override
    @Transactional
    public List<CourseDtoWithId> getFavouriteCourses(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId)));

        return user.getFavouriteCourses().stream()
                .map(this.courseDtoMapper::convertToDtoWithId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteFavouriteCourse(Long userId, Long courseId) {
        CustomUtils.authenticateCurrentUser(userId);

        this.favouriteCoursesRepository.findCourseInFavourites(userId, courseId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId)));

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId)));
        Course course = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId)));

        user.removeCourse(course);
        course.removeUser(user);

        this.courseRepository.save(course);
        this.userRepository.save(user);
    }

    @Override
    @Transactional
    public List<CourseDtoWithId> addCourse(Long userId, Long courseId) {
        CustomUtils.authenticateCurrentUser(userId);

        Integer idx = this.favouriteCoursesRepository.findCourseInFavourites(userId, courseId).orElse(null);
        if (idx != null) {
            throw new EntityAlreadyExistsException(
                    ExceptionMessage.ALREADY_EXISTS.getFormattedMessage("Course", "id", courseId));
        }
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId)));
        Course course = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId)));

        user.addCourse(course);
        course.addUser(user);

        this.courseRepository.save(course);
        this.userRepository.save(user);
        return user.getFavouriteCourses().stream()
                .map(this.courseDtoMapper::convertToDtoWithId)
                .collect(Collectors.toList());
    }
}
