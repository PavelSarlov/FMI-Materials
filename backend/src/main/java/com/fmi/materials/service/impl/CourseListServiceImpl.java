package com.fmi.materials.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import com.fmi.materials.dto.courselist.CourseListDto;
import com.fmi.materials.dto.courselist.CourseListDtoWithId;
import com.fmi.materials.exception.EntityAlreadyExistsException;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.mapper.CourseListDtoMapper;
import com.fmi.materials.model.Course;
import com.fmi.materials.model.CourseList;
import com.fmi.materials.model.User;
import com.fmi.materials.repository.CourseListRepository;
import com.fmi.materials.repository.CourseRepository;
import com.fmi.materials.repository.UserRepository;
import com.fmi.materials.service.CourseListService;
import com.fmi.materials.util.CustomUtils;
import com.fmi.materials.vo.ExceptionMessage;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseListServiceImpl implements CourseListService {

    private final CourseListRepository courseListRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseListDtoMapper courseListDtoMapper;

    private CourseList getCourseListFromRepository(Long courseListId, Long userId) {
        this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId)));

        CourseList courseList = this.courseListRepository.findUserCourseList(userId, courseListId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("CourseList", "id", courseListId)));

        return courseList;
    }

    @Override
    @Transactional
    public CourseListDtoWithId createCourseList(CourseListDto courseListDto, Long userId) {
        CustomUtils.authenticateCurrentUser(userId);

        if (this.courseListRepository.findByListName(userId, courseListDto.getListName()).isPresent()) {
            throw new EntityAlreadyExistsException(ExceptionMessage.ALREADY_EXISTS.getFormattedMessage("CourseList",
                    "name", courseListDto.getListName()));
        }

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId)));
        CourseList courseList = this.courseListDtoMapper.convertToEntity(courseListDto);
        courseList.setUser(user);

        return this.courseListDtoMapper.convertToDtoWithId(this.courseListRepository.save(courseList));
    }

    @Override
    @Transactional
    public CourseListDtoWithId updateCourseList(Long userId, CourseListDtoWithId courseListDtoWithId) {
        CustomUtils.authenticateCurrentUser(userId);

        if (!this.courseListRepository.existsById(courseListDtoWithId.getId())) {
            throw new EntityNotFoundException(
                    ExceptionMessage.NOT_FOUND.getFormattedMessage("CourseList", "id", courseListDtoWithId.getId()));
        }
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId)));

        CourseList courseList = this.courseListDtoMapper.convertToEntityWithId(courseListDtoWithId);
        courseList.setUser(user);
        return this.courseListDtoMapper.convertToDtoWithId(this.courseListRepository.save(courseList));
    }

    @Override
    @Transactional
    public CourseListDtoWithId changeCourseListName(Long userId, Long courseListId, String courseListName) {
        CustomUtils.authenticateCurrentUser(userId);

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId)));

        CourseList courseList = this.courseListRepository.findById(courseListId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("CourseList", "id", courseListId)));
        courseList.setUser(user);
        courseList.setListName(courseListName);
        return this.courseListDtoMapper.convertToDtoWithId(this.courseListRepository.save(courseList));
    }

    @Override
    @Transactional
    public void deleteCourseList(Long userId, Long courseListId) {
        CustomUtils.authenticateCurrentUser(userId);

        if (this.getCourseList(courseListId, userId) != null) {
            this.courseListRepository.deleteById(courseListId);
        }
    }

    @Override
    @Transactional
    public CourseListDtoWithId getCourseList(Long courseListId, Long userId) {
        return this.courseListDtoMapper.convertToDtoWithId(this.getCourseListFromRepository(courseListId, userId));
    }

    @Override
    @Transactional
    public List<CourseListDtoWithId> getAllCourseLists(Long userId) {
        this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId)));

        return this.courseListDtoMapper.convertToDtoList(this.courseListRepository.findUserCourseLists(userId));
    }

    @Override
    @Transactional
    public CourseListDtoWithId addCourseToList(Long courseId, Long courseListId, Long userId) {
        CustomUtils.authenticateCurrentUser(userId);

        Course course = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId)));

        Integer idx = this.courseListRepository.findCourseInList(userId, courseListId, courseId).orElse(null);
        if (idx != null) {
            throw new EntityAlreadyExistsException(
                    ExceptionMessage.ALREADY_EXISTS.getFormattedMessage("Course", "id", courseId));
        }

        CourseList courseList = this.courseListDtoMapper
                .convertToEntityWithId(this.getCourseList(courseListId, userId));
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId)));
        courseList.setUser(user);

        courseList.addCourse(course);
        course.addCourseList(courseList);

        courseList = this.courseListRepository.save(courseList);
        this.courseRepository.save(course);
        return this.courseListDtoMapper.convertToDtoWithId(courseList);
    }

    @Override
    @Transactional
    public void deleteCourseFromCourseList(Long userId, Long courseListId, Long courseId) {
        CustomUtils.authenticateCurrentUser(userId);

        this.courseListRepository.findCourseInList(userId, courseListId, courseId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("Course", "id", courseId)));

        CourseList courseList = this.getCourseListFromRepository(courseListId, userId);
        Course course = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("CourseList", "id", courseListId)));

        courseList.removeCourse(course);
        course.removeCourseList(courseList);

        this.courseRepository.save(course);
        this.courseListRepository.save(courseList);
    }
}
