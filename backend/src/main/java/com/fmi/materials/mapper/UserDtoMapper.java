package com.fmi.materials.mapper;

import java.util.stream.Collectors;

import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.dto.user.UserDtoRegistration;
import com.fmi.materials.dto.user.UserDtoWithId;
import com.fmi.materials.model.User;

import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public UserDto convertToDto(User user) {
        if (user == null) {
            return null;
        }

        return new UserDto(
                user.getName(),
                null,
                user.getEmail(),
                user.getRoles() != null ? user.getRoles().stream()
                        .map(r -> r.getName())
                        .collect(Collectors.toList()) : null);
    }

    public UserDtoWithId convertToDtoWithId(User user) {
        if (user == null) {
            return null;
        }

        return new UserDtoWithId(
                user.getId(),
                user.getName(),
                null,
                user.getEmail(),
                user.getRoles() != null ? user.getRoles().stream()
                        .map(r -> r.getName())
                        .collect(Collectors.toList()) : null);
    }

    public User convertToEntity(UserDtoRegistration userDto) {
        if (userDto == null) {
            return null;
        }

        return new User(
                userDto.getName(),
                userDto.getPassword(),
                userDto.getEmail());
    }

    public User convertToEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return new User(userDto.getName(),
                userDto.getPassword(),
                userDto.getEmail());
    }

    public User convertToEntityWithId(UserDtoWithId userDto) {
        if (userDto == null) {
            return null;
        }

        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getPassword(),
                userDto.getEmail());
    }
}
