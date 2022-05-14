package com.fmi.materials.mapper;

import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.dto.user.UserDtoRegistration;
import com.fmi.materials.dto.user.UserDtoWithId;
import com.fmi.materials.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class UserDtoMapper {
    public UserDto convertToDto(User user) {
        return new UserDto(user.getName(), user.getPasswordHash(), user.getEmail());
    }

    public UserDtoWithId convertToDtoWithId(User user) {
        return new UserDtoWithId(user.getId(), user.getName(), user.getPasswordHash(), user.getEmail());
    }

    public User convertToEntity(UserDtoRegistration userDto) {
        return new User(userDto.getName(), userDto.getPassword(), userDto.getEmail());
    }

    public User convertToEntity(UserDto userDto) {
        return new User(userDto.getName(), userDto.getPassword(), userDto.getEmail());
    }

    public User convertToEntityWithId(UserDtoWithId userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getPassword(), userDto.getEmail());
    }
}
