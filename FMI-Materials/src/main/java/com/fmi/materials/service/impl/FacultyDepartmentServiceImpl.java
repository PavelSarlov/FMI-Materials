package com.fmi.materials.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.fmi.materials.dto.facultydepartment.FacultyDepartmentDto;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.mapper.FacultyDepartmentDtoMapper;
import com.fmi.materials.model.FacultyDepartment;
import com.fmi.materials.repository.FacultyDepartmentRepository;
import com.fmi.materials.service.FacultyDepartmentService;
import com.fmi.materials.vo.ErrorMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacultyDepartmentServiceImpl implements FacultyDepartmentService {

    @Autowired
    FacultyDepartmentRepository facultyDepartmentRepository;
    @Autowired
    FacultyDepartmentDtoMapper facultyDepartmentDtoMapper;

    @Override
    public FacultyDepartmentDto createFacultyDepartment(FacultyDepartmentDto facultyDepartmentDto) {
        FacultyDepartment facultyDepartment = this.facultyDepartmentDtoMapper.convertToEntity(facultyDepartmentDto);
        return this.facultyDepartmentDtoMapper.convertToDto(this.facultyDepartmentRepository.save(facultyDepartment));
    }

    @Override
    public void deleteFacultyDepartment(Long facultyDepartmentId) {
        if(!this.facultyDepartmentRepository.existsById(facultyDepartmentId)) {
            throw new EntityNotFoundException(ErrorMessage.NOT_FOUND.getFormattedMessage("Faculty department", "id", facultyDepartmentId));
        }
        this.facultyDepartmentRepository.deleteById(facultyDepartmentId);
    }

    @Override
    public FacultyDepartmentDto updateFacultyDepartment(FacultyDepartmentDto facultyDepartmentDto) {
        if (!this.facultyDepartmentRepository.existsById(facultyDepartmentDto.getId())) {
            throw new EntityNotFoundException(ErrorMessage.NOT_FOUND.getFormattedMessage("Faculty department", "id", facultyDepartmentDto.getId()));
        }
        FacultyDepartment facultyDepartment = this.facultyDepartmentDtoMapper.convertToEntity(facultyDepartmentDto);
        return this.facultyDepartmentDtoMapper.convertToDto(this.facultyDepartmentRepository.save(facultyDepartment));
    }

    @Override
    public FacultyDepartmentDto findById(Long facultyDepartmentId) {
        return this.facultyDepartmentDtoMapper.convertToDto(this.facultyDepartmentRepository
                .findById(facultyDepartmentId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.NOT_FOUND.getFormattedMessage("Faculty department", "id", facultyDepartmentId))));
    }

    @Override
    public List<FacultyDepartmentDto> findAllFacultyDepartments() {
        return this.facultyDepartmentRepository.findAll().stream()
                .map(this.facultyDepartmentDtoMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
