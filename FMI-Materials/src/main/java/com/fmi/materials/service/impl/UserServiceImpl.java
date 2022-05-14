package com.fmi.materials.service.impl;

import java.util.NoSuchElementException;

import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.dto.user.UserDtoRegistration;
import com.fmi.materials.dto.user.UserDtoWithId;
import com.fmi.materials.mapper.UserDtoMapper;
import com.fmi.materials.model.User;
import com.fmi.materials.repository.UserRepository;
import com.fmi.materials.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final String INSTANCE_NOT_FOUND = "Object with id: '%s', nof found.";
    private final String PASSWORDS_NOT_EQUAL = "The inputted passwords are not equal.";
    private final String EMAIL_TAKEN = "The email already exists.";
    private final String NO_SUCH_USER = "Wrong credentials.";

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
            throw new IllegalArgumentException(PASSWORDS_NOT_EQUAL);
        }
        else if(this.userRepository.findByEmail(userDto.getEmail())!=null) {
            throw new IllegalArgumentException(EMAIL_TAKEN);
        }
        userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        
        User user = this.userDtoMapper.convertToEntity(userDto);
        return this.userDtoMapper.convertToDto(this.userRepository.save(user));
    }

    @Override
    public void deleteUserById(Long id) {
        if(!this.userRepository.existsById(id)) {
            throw new NoSuchElementException(String.format(INSTANCE_NOT_FOUND, id));
        }

        this.userRepository.deleteById(id);
    }

    @Override
    public UserDto updateUser(UserDtoWithId userDtoWithId) {
        if(!this.userRepository.existsById(userDtoWithId.getId())) {
            throw new NoSuchElementException(String.format(INSTANCE_NOT_FOUND, userDtoWithId.getId()));
        }

        User user = this.userDtoMapper.convertToEntityWithId(userDtoWithId);
        return this.userDtoMapper.convertToDto(this.userRepository.save(user));
    }

    @Override
    public UserDtoWithId findUserById(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format(INSTANCE_NOT_FOUND, id)));
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
            throw new NoSuchElementException(NO_SUCH_USER);
        }
    }
}
