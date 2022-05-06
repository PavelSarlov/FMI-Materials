package com.fmi.materials.service;

import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.dto.user.UserDtoWithId;

public interface UserService {
    UserDto createUser(UserDto userDto);

    void deleteUserById(Long id);

    UserDto updateUser(UserDtoWithId userDtoWithId);

    UserDtoWithId findUserById(Long id);
}
