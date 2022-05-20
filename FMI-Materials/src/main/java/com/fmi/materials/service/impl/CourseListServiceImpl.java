package com.fmi.materials.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.fmi.materials.dto.course.CourseDtoWithId;
import com.fmi.materials.dto.courselist.CourseListDto;
import com.fmi.materials.dto.courselist.CourseListDtoWithId;
import com.fmi.materials.exception.EntityAlreadyExistsException;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.exception.InvalidArgumentException;
import com.fmi.materials.mapper.CourseDtoMapper;
import com.fmi.materials.mapper.CourseListDtoMapper;
import com.fmi.materials.mapper.UserDtoMapper;
import com.fmi.materials.model.Course;
import com.fmi.materials.model.CourseList;
import com.fmi.materials.model.CustomUserDetails;
import com.fmi.materials.model.User;
import com.fmi.materials.repository.CourseListRepository;
import com.fmi.materials.repository.CourseRepository;
import com.fmi.materials.service.CourseListService;
import com.fmi.materials.service.UserService;
import com.fmi.materials.vo.ExceptionMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CourseListServiceImpl implements CourseListService {

    private CourseListRepository courseListRepository;
    private CourseRepository courseRepository;
    private UserService userService;
    private CourseListDtoMapper courseListDtoMapper;
    private UserDtoMapper userDtoMapper;
    private CourseDtoMapper courseDtoMapper;

    @Autowired
    public CourseListServiceImpl(
            CourseListRepository courseListRepository,
            CourseRepository courseRepository,
            UserService userService,
            CourseListDtoMapper courseListDtoMapper,
            UserDtoMapper userDtoMapper,
            CourseDtoMapper courseDtoMapper
    ) {
        this.courseListRepository = courseListRepository;
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.courseListDtoMapper = courseListDtoMapper;
        this.userDtoMapper = userDtoMapper;
        this.courseDtoMapper = courseDtoMapper;
    }

    private Boolean authenticateCurrentUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Long loggedUserId = userDetails.getId();

        if(loggedUserId != userId) {
            return false;
        }
        return true;
    }

    @Override
    public CourseListDtoWithId createCourseList(CourseListDto courseListDto, Long userId) {
        if (this.courseListRepository.findByListName(courseListDto.getListName()) != null) {
            throw new EntityAlreadyExistsException(ExceptionMessage.ALREADY_EXISTS.getFormattedMessage("CourseList", "name", courseListDto.getListName()));
        }
        try {
            if (!authenticateCurrentUser(userId)) {
                throw new InvalidArgumentException(ExceptionMessage.INVALID_OPERATION.getFormattedMessage());
            }

            User user = this.userDtoMapper.convertToEntityWithId(this.userService.findUserById(userId));
            CourseList courseList = this.courseListDtoMapper.convertToEntity(courseListDto);
            courseList.setUser(user);

            return this.courseListDtoMapper.convertToDtoWithId(this.courseListRepository.save(courseList));
        } catch (Exception e) {
            throw new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId));
        }
    }

    @Override
    public CourseListDtoWithId updateCourseList(Long userId, CourseListDtoWithId courseListDtoWithId) {
        if (!authenticateCurrentUser(userId)) {
            throw new InvalidArgumentException(ExceptionMessage.INVALID_OPERATION.getFormattedMessage());
        }

        if (!this.courseListRepository.existsById(courseListDtoWithId.getId())) {
            throw new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("CourseList", "id", courseListDtoWithId.getId()));
        }

        CourseList courseList = this.courseListDtoMapper.convertToEntityWithId(courseListDtoWithId);
        return this.courseListDtoMapper.convertToDtoWithId(this.courseListRepository.save(courseList));
    }

    @Override
    public void deleteCourseList(Long userId, Long courseListId) {
        if (!authenticateCurrentUser(userId)) {
            throw new InvalidArgumentException(ExceptionMessage.INVALID_OPERATION.getFormattedMessage());
        }

        if (this.getCourseList(courseListId, userId) != null) {
            this.courseListRepository.deleteById(courseListId);
        }
    }

    @Override
    public CourseListDtoWithId getCourseList(Long courseListId, Long userId) {
        if (this.userService.findUserById(userId) == null) {
            throw new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId));
        }

        CourseList courseList = this.courseListRepository.findUserCourseList(userId, courseListId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("CourseList", "id", courseListId)));

        return this.courseListDtoMapper.convertToDtoWithId(courseList);
    }

    @Override
    public List<CourseListDtoWithId> getAllCourseLists(Long userId) {
        if (this.userService.findUserById(userId) == null) {
            throw new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId));
        }

        return this.courseListDtoMapper.convertToDtoList(this.courseListRepository.findUserCourseLists(userId));
    }

    @Override
    public CourseListDtoWithId addCourseToList(Long courseId, Long courseListId, Long userId) {
        try {
            if (!authenticateCurrentUser(userId)) {
                throw new InvalidArgumentException(ExceptionMessage.INVALID_OPERATION.getFormattedMessage());
            }

            Course course = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("CourseList", "id", courseListId)));

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

    @Override
    public void deleteCourseFromCourseList(Long userId, Long courseListId, Long courseId) {
        try {
            if (!authenticateCurrentUser(userId)) {
                throw new InvalidArgumentException(ExceptionMessage.INVALID_OPERATION.getFormattedMessage());
            }

            CourseList courseList = this.courseListDtoMapper.convertToEntityWithId(this.getCourseList(courseListId, userId));
            User user = this.userDtoMapper.convertToEntityWithId(this.userService.findUserById(userId));
            courseList.setUser(user);

            Course course = this.courseRepository.findById(courseId)
                    .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("CourseList", "id", courseListId)));

            courseList.removeCourse(course);
            course.removeCourseList(courseList);

            this.courseRepository.save(course);
            this.courseListRepository.save(courseList);
        } catch (Exception e) {
            throw e;
        }
    }
}
