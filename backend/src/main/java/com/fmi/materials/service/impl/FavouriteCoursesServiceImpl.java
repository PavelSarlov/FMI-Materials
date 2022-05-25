package com.fmi.materials.service.impl;

import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.mapper.CourseDtoMapper;
import com.fmi.materials.mapper.UserDtoMapper;
import com.fmi.materials.model.Course;
import com.fmi.materials.model.User;
import com.fmi.materials.repository.CourseListRepository;
import com.fmi.materials.repository.CourseRepository;
import com.fmi.materials.repository.UserRepository;
import com.fmi.materials.service.FavouriteCoursesService;
import com.fmi.materials.util.Authentication;
import com.fmi.materials.vo.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavouriteCoursesServiceImpl implements FavouriteCoursesService {
    private CourseRepository courseRepository;
    private CourseListRepository courseListRepository;
    private UserRepository userRepository;
    private UserDtoMapper userDtoMapper;
    private CourseDtoMapper courseDtoMapper;

    @Autowired
    public FavouriteCoursesServiceImpl(
            CourseRepository courseRepository,
            CourseListRepository courseListRepository,
            UserRepository userRepository,
            UserDtoMapper userDtoMapper,
            CourseDtoMapper courseDtoMapper
    ) {
        this.courseRepository = courseRepository;
        this.courseListRepository = courseListRepository;
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
        this.courseDtoMapper = courseDtoMapper;
    }

    @Override
    public List<CourseDtoWithId> getFavouriteCourses(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId)));

        return user.getFavouriteCourses().stream()
                .map(this.courseDtoMapper::convertToDtoWithId)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFavouriteCourse(Long userId, Long courseId) {
        Authentication.authenticateCurrentUser(userId);

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId)));
        Course course = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId)));

        user.removeCourse(course);
        course.removeUser(user);

        this.courseRepository.save(course);
        this.userRepository.save(user);
    }

    @Override
    public List<CourseDtoWithId> addCourse(Long userId, Long courseId) {
        Authentication.authenticateCurrentUser(userId);

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId)));
        Course course = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId)));

        user.addCourse(course);
        course.addUser(user);

        this.courseRepository.save(course);
        this.userRepository.save(user);
        return user.getFavouriteCourses().stream()
                .map(this.courseDtoMapper::convertToDtoWithId)
                .collect(Collectors.toList());
    }
}
