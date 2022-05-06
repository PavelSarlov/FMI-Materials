package com.fmi.materials.service;

import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.dto.user.UserDtoWithId;

public interface UserService {
    UserDto createUser(UserDto userDto);

    void deleteUser(Long id);

    UserDto updateUser(UserDtoWithId userDtoWithId);

    UserDto findUserById(Long id);
}
