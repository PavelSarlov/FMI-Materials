package com.fmi.materials.service;

import com.fmi.materials.dto.courselist.CourseListDto;
import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.dto.user.UserDtoWithId;
import com.fmi.materials.mapper.CourseListDtoMapper;
import com.fmi.materials.mapper.UserDtoMapper;
import com.fmi.materials.model.User;
import com.fmi.materials.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    private final String INSTANCE_NOT_FOUND = "User with id: '%s', nof found.";

    private UserRepository userRepository;
    private UserDtoMapper userDtoMapper;
    private CourseListDtoMapper courseListDtoMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDtoMapper userDtoMapper, CourseListDtoMapper courseListDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
        this.courseListDtoMapper = courseListDtoMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
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
        User user = this.userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format(INSTANCE_NOT_FOUND, id)));
        return this.userDtoMapper.convertToDtoWithId(user);
    }
}
