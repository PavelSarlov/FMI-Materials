package com.fmi.materials.service.impl;

import java.io.IOException;

import javax.transaction.Transactional;

import com.fmi.materials.dto.materialrequest.MaterialRequestDto;
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
import com.fmi.materials.repository.MaterialRepository;
import com.fmi.materials.repository.MaterialRequestRepository;
import com.fmi.materials.repository.SectionRepository;
import com.fmi.materials.repository.UserRepository;
import com.fmi.materials.service.UserService;
import com.fmi.materials.service.WebSocketService;
import com.fmi.materials.util.CustomUtils;
import com.fmi.materials.vo.ExceptionMessage;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final SectionRepository sectionRepository;
    private final MaterialRequestRepository materialRequestRepository;
    private final MaterialRepository materialRepository;
    private final MaterialRequestDtoMapper materialRequestDtoMapper;
    private final WebSocketService webSocketService;

    @Override
    @Transactional
    public UserDto createUser(UserDtoRegistration userDto) {
        if (!userDto.getPassword().equals(userDto.getRepeatedPassword())) {
            throw new InvalidArgumentException(ExceptionMessage.PASSWORDS_NOT_EQUAL.getFormattedMessage());
        } else if (this.userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException(
                    ExceptionMessage.ALREADY_EXISTS.getFormattedMessage("User", "email", userDto.getEmail()));
        }
        userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

        User user = this.userDtoMapper.convertToEntity(userDto);
        return this.userDtoMapper.convertToDto(this.userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        if (!this.userRepository.existsById(id)) {
            throw new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", id));
        }

        this.userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDtoWithId userDtoWithId) {
        if (!this.userRepository.existsById(userDtoWithId.getId())) {
            throw new EntityNotFoundException(
                    ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userDtoWithId.getId()));
        }

        User user = this.userDtoMapper.convertToEntityWithId(userDtoWithId);
        return this.userDtoMapper.convertToDto(this.userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDtoWithId findUserById(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", id)));
        return this.userDtoMapper.convertToDtoWithId(user);
    }

    @Override
    @Transactional
    public UserDtoWithId findUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "email", email)));
        return this.userDtoMapper.convertToDtoWithId(user);
    }

    @Override
    @Transactional
    public MaterialRequestDto createMaterialRequest(MaterialRequestDto materialRequestDto, Long sectionId, Long userId)
            throws IOException {
        CustomUtils.authenticateCurrentUser(userId);

        Section section = this.sectionRepository.findById(sectionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("Section", "id", sectionId)));

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "id", userId)));

        if (this.materialRequestRepository.findBySectionAndFileName(section.getId(), materialRequestDto.getFileName())
                .isPresent()) {
            throw new EntityAlreadyExistsException(ExceptionMessage.ALREADY_EXISTS
                    .getFormattedMessage("Material request", "filename", materialRequestDto.getFileName()));
        }

        if (this.materialRepository.findBySectionAndFileName(section.getId(), materialRequestDto.getFileName())
                .isPresent()) {
            throw new EntityAlreadyExistsException(ExceptionMessage.ALREADY_EXISTS.getFormattedMessage("Material",
                    "filename", materialRequestDto.getFileName()));
        }

        MaterialRequest materialRequest = this.materialRequestDtoMapper.convertToEntity(materialRequestDto);
        materialRequest.setSection(section);
        materialRequest.setUser(user);

        String courseAdmin = section.getCourse().getCreatedBy();

        this.webSocketService.notifyFrontedUser(courseAdmin, "request");

        return this.materialRequestDtoMapper.convertToDto(this.materialRequestRepository.save(materialRequest));
    }

    @Override
    @Transactional
    public UserDtoWithId authenticateUser(UserDto userDto) {
        User user = this.userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        ExceptionMessage.NOT_FOUND.getFormattedMessage("User", "email", userDto.getEmail())));

        if (!this.passwordEncoder.matches(userDto.getPassword(), user.getPasswordHash())) {
            throw new InvalidArgumentException(ExceptionMessage.LOGIN_INVALID.getFormattedMessage());
        }

        return this.userDtoMapper.convertToDtoWithId(user);
    }
}
