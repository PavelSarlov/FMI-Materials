package com.fmi.materials.service;

import com.fmi.materials.dto.courselist.CourseListDto;
import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.dto.user.UserDtoRegistration;
import com.fmi.materials.dto.user.UserDtoWithId;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDtoRegistration userDto);

    void deleteUserById(Long id);

    UserDto updateUser(UserDtoWithId userDtoWithId);

    UserDtoWithId findUserById(Long id);
}
