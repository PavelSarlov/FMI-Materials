package com.fmi.materials.service;

import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.dto.user.UserDtoRegistration;
import com.fmi.materials.dto.user.UserDtoWithId;
import com.fmi.materials.model.CourseList;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDtoRegistration userDto);

    void deleteUserById(Long id);

    UserDto updateUser(UserDtoWithId userDtoWithId);

    UserDtoWithId findUserById(Long id);

    Long existsUser(UserDto userDto);

    List<CourseList> getAllCourseLists(Long id);
}
