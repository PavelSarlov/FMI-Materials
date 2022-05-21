package com.fmi.materials.service.impl;

import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.dto.user.UserDtoRegistration;
import com.fmi.materials.dto.user.UserDtoWithId;
import com.fmi.materials.exception.EntityAlreadyExistsException;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.exception.InvalidArgumentException;
import com.fmi.materials.mapper.UserDtoMapper;
import com.fmi.materials.model.User;
import com.fmi.materials.repository.UserRepository;
import com.fmi.materials.service.UserService;
import com.fmi.materials.vo.ExceptionMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserDtoMapper userDtoMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDtoMapper userDtoMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createUser(UserDtoRegistration userDto) {
        if(!userDto.getPassword().equals(userDto.getRepeatedPassword())) {
            throw new InvalidArgumentException(ExceptionMessage.PASSWORDS_NOT_EQUAL.getFormattedMessage());
        }
        else if(this.userRepository.findByEmail(userDto.getEmail())!=null) {
            throw new EntityAlreadyExistsException(ExceptionMessage.ALREADY_EXISTS.getFormattedMessage("User", "email", userDto.getEmail()));
        }
        userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        
        User user = this.userDtoMapper.convertToEntity(userDto);
        return this.userDtoMapper.convertToDto(this.userRepository.save(user));
    }

    @Override
    public void deleteUserById(Long id) {
        if(!this.userRepository.existsById(id)) {
            throw new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", id));
        }

        this.userRepository.deleteById(id);
    }

    @Override
    public UserDto updateUser(UserDtoWithId userDtoWithId) {
        if(!this.userRepository.existsById(userDtoWithId.getId())) {
            throw new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userDtoWithId.getId()));
        }

        User user = this.userDtoMapper.convertToEntityWithId(userDtoWithId);
        return this.userDtoMapper.convertToDto(this.userRepository.save(user));
    }

    @Override
    public UserDtoWithId findUserById(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", id)));
        return this.userDtoMapper.convertToDtoWithId(user);
    }

    @Override
    public Long existsUser(UserDto userDto) {
        // won't work
        Long userId = this.userRepository.findUserByNameAndEmailAndPassword(userDto.getName(), userDto.getEmail(), userDto.getPassword());
        if (userId > 0) {
            return userId;
        }
        else {
            throw new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId));
        }
    }
}
