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
    public CourseListDto addCourseToList(Long courseId, Long courseListId, Long userId) {
        Course course = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException(String.format(INSTANCE_NOT_FOUND, courseId)));

        try {
            CourseList courseList = this.courseListDtoMapper.convertToEntityWithId(this.getCourseList(courseListId, userId));
            //course.getCourseLists().add(courseList);
            //courseList.getCourses().add(course);

            return this.courseListDtoMapper.convertToDto(courseList);
        } catch (Exception e) {
            String listName = "List %s";
            CourseList currentList = new CourseList(String.format(listName, courseListId));
            ;
            currentList = this.courseListRepository.save(currentList);
            //course.getCourseLists().add(currentList);
            //currentList.getCourses().add(course);

            return this.courseListDtoMapper.convertToDto(currentList);
        }
    }
}
