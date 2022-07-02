package com.fmi.materials.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.mapper.FacultyDepartmentDtoMapper;
import com.fmi.materials.model.FacultyDepartment;
import com.fmi.materials.repository.FacultyDepartmentRepository;
import com.fmi.materials.service.FacultyDepartmentService;
import com.fmi.materials.vo.ExceptionMessage;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacultyDepartmentServiceImpl implements FacultyDepartmentService {

    private final FacultyDepartmentRepository facultyDepartmentRepository;
    private final FacultyDepartmentDtoMapper facultyDepartmentDtoMapper;

    @Override
    @Transactional
    public FacultyDepartmentDto createFacultyDepartment(FacultyDepartmentDto facultyDepartmentDto) {
        FacultyDepartment facultyDepartment = this.facultyDepartmentDtoMapper.convertToEntity(facultyDepartmentDto);
        return this.facultyDepartmentDtoMapper.convertToDto(this.facultyDepartmentRepository.save(facultyDepartment));
    }

    @Override
    @Transactional
    public void deleteFacultyDepartment(Long facultyDepartmentId) {
        if (!this.facultyDepartmentRepository.existsById(facultyDepartmentId)) {
            throw new EntityNotFoundException(
                    ExceptionMessage.NOT_FOUND.getFormattedMessage("Faculty department", "id", facultyDepartmentId));
        }
        this.facultyDepartmentRepository.deleteById(facultyDepartmentId);
    }

    @Override
    @Transactional
    public FacultyDepartmentDto updateFacultyDepartment(FacultyDepartmentDto facultyDepartmentDto) {
        if (!this.facultyDepartmentRepository.existsById(facultyDepartmentDto.getId())) {
            throw new EntityNotFoundException(ExceptionMessage.NOT_FOUND.getFormattedMessage("Faculty department", "id",
                    facultyDepartmentDto.getId()));
        }
        FacultyDepartment facultyDepartment = this.facultyDepartmentDtoMapper.convertToEntity(facultyDepartmentDto);
        return this.facultyDepartmentDtoMapper.convertToDto(this.facultyDepartmentRepository.save(facultyDepartment));
    }

    @Override
    @Transactional
    public FacultyDepartmentDto findById(Long facultyDepartmentId) {
        return this.facultyDepartmentDtoMapper.convertToDto(this.facultyDepartmentRepository
                .findById(facultyDepartmentId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND
                        .getFormattedMessage("Faculty department", "id", facultyDepartmentId))));
    }

    @Override
    @Transactional
    public List<FacultyDepartmentDto> findAllFacultyDepartments() {
        return this.facultyDepartmentRepository.findAll().stream()
                .map(this.facultyDepartmentDtoMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
