package com.fmi.materials.service.impl;

import com.fmi.materials.dto.materialrequest.MaterialRequestDto;
import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.response.ResponseDtoSuccess;
import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.dto.user.UserDtoRegistration;
import com.fmi.materials.dto.user.UserDtoWithId;
import com.fmi.materials.exception.EntityAlreadyExistsException;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.exception.InvalidArgumentException;
import com.fmi.materials.mapper.MaterialRequestDtoMapper;
import com.fmi.materials.mapper.UserDtoMapper;
import com.fmi.materials.model.MaterialRequest;
import com.fmi.materials.model.Section;
import com.fmi.materials.model.User;
import com.fmi.materials.repository.MaterialRequestRepository;
import com.fmi.materials.repository.SectionRepository;
import com.fmi.materials.repository.UserRepository;
import com.fmi.materials.service.UserService;
import com.fmi.materials.util.Authentication;
import com.fmi.materials.vo.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserDtoMapper userDtoMapper;
    private PasswordEncoder passwordEncoder;
    private SectionRepository sectionRepository;
    private MaterialRequestRepository materialRequestRepository;
    private MaterialRequestDtoMapper materialRequestDtoMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserDtoMapper userDtoMapper,
                           PasswordEncoder passwordEncoder,
                           SectionRepository sectionRepository,
                           MaterialRequestRepository materialRequestRepository,
                           MaterialRequestDtoMapper materialRequestDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
        this.passwordEncoder = passwordEncoder;
        this.sectionRepository = sectionRepository;
        this.materialRequestRepository = materialRequestRepository;
        this.materialRequestDtoMapper = materialRequestDtoMapper;
    }

    @Override
    public UserDto createUser(UserDtoRegistration userDto) {
        if (!userDto.getPassword().equals(userDto.getRepeatedPassword())) {
            throw new InvalidArgumentException(ExceptionMessage.PASSWORDS_NOT_EQUAL.getFormattedMessage());
        } else if (this.userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException(ExceptionMessage.ALREADY_EXISTS.getFormattedMessage("User", "email", userDto.getEmail()));
        }
        userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

        User user = this.userDtoMapper.convertToEntity(userDto);
        return this.userDtoMapper.convertToDto(this.userRepository.save(user));
    }

    @Override
    public void deleteUserById(Long id) {
        if (!this.userRepository.existsById(id)) {
            throw new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", id));
        }

        this.userRepository.deleteById(id);
    }

    @Override
    public UserDto updateUser(UserDtoWithId userDtoWithId) {
        if (!this.userRepository.existsById(userDtoWithId.getId())) {
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
    public UserDtoWithId findUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "email", email)));
        return this.userDtoMapper.convertToDtoWithId(user);
    }

    @Override
    public Long existsUser(UserDto userDto) {
        // won't work
        Long userId = this.userRepository.findUserByNameAndEmailAndPassword(userDto.getName(), userDto.getEmail(), userDto.getPassword());
        if (userId > 0) {
            return userId;
        } else {
            throw new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId));
        }
    }

    @Override
    public MaterialRequestDto createMaterialRequest(MultipartFile materialFile, Long sectionId, Long userId) throws IOException {
        Authentication.authenticateCurrentUser(userId);

        Section section = this.sectionRepository.findById(sectionId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Section", "id", sectionId)));

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId)));

        MaterialRequest materialRequest = this.materialRequestDtoMapper.convertToEntity(materialFile, user, section);
        materialRequest = this.materialRequestRepository.save(materialRequest);
        return this.materialRequestDtoMapper.convertToDto(materialRequest);
    }

    @Override
    public ResponseDto loginUser(UserDto userDto) {
        User user = this.userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "email", userDto.getEmail())));

        if (!this.passwordEncoder.matches(userDto.getPassword(), user.getPasswordHash())) {
            throw new InvalidArgumentException(ExceptionMessage.LOGIN_INVALID.getFormattedMessage());
        }

        org.springframework.security.core.Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPasswordHash(), user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseDtoSuccess(
                HttpStatus.OK,
                "Login successful"
        );
    }

    @Override
    public ResponseDto logoutUser() {
        SecurityContextHolder.getContext().setAuthentication(null);

        return new ResponseDtoSuccess(
                HttpStatus.OK,
                "Logout successful"
        );
    }
}
