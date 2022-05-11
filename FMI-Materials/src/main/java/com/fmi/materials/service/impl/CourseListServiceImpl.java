package com.fmi.materials.service.impl;

import com.fmi.materials.dto.courselist.CourseListDto;
import com.fmi.materials.dto.courselist.CourseListDtoWithId;
import com.fmi.materials.mapper.CourseListDtoMapper;
import com.fmi.materials.mapper.UserDtoMapper;
import com.fmi.materials.model.Course;
import com.fmi.materials.model.CourseList;
import com.fmi.materials.model.User;
import com.fmi.materials.repository.CourseListRepository;
import com.fmi.materials.repository.CourseRepository;
import com.fmi.materials.service.CourseListService;
import com.fmi.materials.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CourseListServiceImpl implements CourseListService {
    private final String INSTANCE_NOT_FOUND = "Object with id: '%s', nof found.";
    private final String INSTANCE_EXISTS = "Object with name: '%s', already exists.";

    private CourseListRepository courseListRepository;
    private CourseRepository courseRepository;
    private UserService userService;
    private CourseListDtoMapper courseListDtoMapper;
    private UserDtoMapper userDtoMapper;

    @Autowired
    public CourseListServiceImpl(
            CourseListRepository courseListRepository,
            CourseRepository courseRepository,
            UserService userService,
            CourseListDtoMapper courseListDtoMapper,
            UserDtoMapper userDtoMapper
    ) {
        this.courseListRepository = courseListRepository;
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.courseListDtoMapper = courseListDtoMapper;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public CourseListDtoWithId createCourseList(CourseListDto courseListDto, Long userId) {
        if (this.courseListRepository.findByListName(courseListDto.getListName()) != null) {
            throw new IllegalArgumentException(String.format(INSTANCE_EXISTS, courseListDto.getListName()));
        }
        try {
            User user = this.userDtoMapper.convertToEntityWithId(this.userService.findUserById(userId));
            CourseList courseList = this.courseListDtoMapper.convertToEntity(courseListDto);
            courseList.setUser(user);

            return this.courseListDtoMapper.convertToDtoWithId(this.courseListRepository.save(courseList));
        } catch (Exception e) {
            throw new NoSuchElementException(String.format(INSTANCE_NOT_FOUND, userId));
        }
    }

    @Override
    public CourseListDtoWithId updateCourseList(CourseListDtoWithId courseListDtoWithId) {
        if (!this.courseListRepository.existsById(courseListDtoWithId.getId())) {
            throw new NoSuchElementException(String.format(INSTANCE_NOT_FOUND, courseListDtoWithId.getId()));
        }

        CourseList courseList = this.courseListDtoMapper.convertToEntityWithId(courseListDtoWithId);
        return this.courseListDtoMapper.convertToDtoWithId(this.courseListRepository.save(courseList));
    }

    @Override
    public void deleteCourseList(Long userId, Long courseListId) {
        if (this.courseListRepository.findUserCourseList(userId, courseListId) == null) {
            throw new NoSuchElementException(String.format(INSTANCE_NOT_FOUND, courseListId));
        }

        this.courseListRepository.deleteById(courseListId);
    }

    @Override
    public CourseListDtoWithId getCourseList(Long courseListId, Long userId) {
        if (this.userService.findUserById(userId) == null) {
            throw new NoSuchElementException(String.format(INSTANCE_NOT_FOUND, userId));
        }

        CourseList courseList = this.courseListRepository.findUserCourseList(userId, courseListId)
                .orElseThrow(() -> new NoSuchElementException(String.format(INSTANCE_NOT_FOUND,courseListId)));

        return this.courseListDtoMapper.convertToDtoWithId(courseList);
    }

    @Override
    public List<CourseListDtoWithId> getAllCourseLists(Long userId) {
        if (this.userService.findUserById(userId) == null) {
            throw new NoSuchElementException(String.format(INSTANCE_NOT_FOUND, userId));
        }

        return this.courseListDtoMapper.convertToDtoList(this.courseListRepository.findUserCourseLists(userId));
    }

    @Override
    public CourseListDtoWithId addCourseToList(Long courseId, Long courseListId, Long userId) {
        try {
            Course course = this.courseRepository.findById(courseId)
                    .orElseThrow(() -> new NoSuchElementException(String.format(INSTANCE_NOT_FOUND, courseId)));

            CourseList courseList = this.courseListDtoMapper.convertToEntityWithId(this.getCourseList(courseListId, userId));
            User user = this.userDtoMapper.convertToEntityWithId(this.userService.findUserById(userId));
            courseList.setUser(user);

            courseList.addCourse(course);
            course.addCourseList(courseList);

            courseList = this.courseListRepository.save(courseList);
            this.courseRepository.save(course);
            return this.courseListDtoMapper.convertToDtoWithId(courseList);
        } catch (Exception e) {
            throw e;
        }
    }
}
