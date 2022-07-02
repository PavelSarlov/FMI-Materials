package com.fmi.materials.service;

import java.io.IOException;

import com.fmi.materials.dto.materialrequest.MaterialRequestDto;
import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.dto.user.UserDtoRegistration;
import com.fmi.materials.dto.user.UserDtoWithId;

public interface UserService {
    UserDto createUser(UserDtoRegistration userDto);

    void deleteUserById(Long id);

    UserDto updateUser(UserDtoWithId userDtoWithId);

    UserDtoWithId findUserById(Long id);

    UserDtoWithId findUserByEmail(String email);

    MaterialRequestDto createMaterialRequest(MaterialRequestDto materialRequestDto, Long sectionId, Long userId)
            throws IOException;

    UserDtoWithId authenticateUser(UserDto userDto);
}
